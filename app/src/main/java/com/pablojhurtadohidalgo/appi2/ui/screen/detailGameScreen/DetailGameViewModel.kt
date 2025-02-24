package com.pablojhurtadohidalgo.appi2.ui.screen.detailGameScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.pablojhurtadohidalgo.appi2.data.model.MediaItem
import com.pablojhurtadohidalgo.appi2.data.model.MediaJuego
import com.pablojhurtadohidalgo.appi2.data.repositories.RemoteConnection
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DetailGameViewModel(name:String?): ViewModel() {
    private val _lista: MutableLiveData<List<MediaItem>> = MutableLiveData()
    val lista: LiveData<List<MediaItem>> = _lista

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    private val _user : MutableLiveData<FirebaseUser> = MutableLiveData()
    val user: LiveData<FirebaseUser> = _user

    private val firestore = FirebaseFirestore.getInstance()


    init{

        viewModelScope.launch(){
            _progressBar.value=true

            val pokemons = getJuegos(name)

            _lista.value = pokemons
            _progressBar.value = false
        }
    }
    private suspend fun getJuegos(name:String?): List<MediaItem> {
        return try{
            var listaPokemon=mutableListOf<MediaItem>()
            val pokemons = RemoteConnection.service.getPokemons()
            listaPokemon=pokemons.results.map{
                val id=it.url.split('/').last{it.isNotEmpty()}.toInt()
                val pokemonsDetail= RemoteConnection.service.getPokemonDetails(id)
                var idPkm=0
                var namePkm=""
                var sprite=""
                for(document in pokemonsDetail.game_indices){
                    if(document.version.name==name){
                        idPkm=id
                        namePkm=it.name
                        sprite=pokemonsDetail.sprites.front_default
                    }

                }
                MediaItem(
                    idPkm,
                    namePkm,
                    sprite
                )
            }.toMutableList()


            listaPokemon
        }catch(e:Exception){
            emptyList()
        }
    }
}