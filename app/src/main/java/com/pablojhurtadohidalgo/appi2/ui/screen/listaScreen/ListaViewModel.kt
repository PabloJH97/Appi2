package com.pablojhurtadohidalgo.appi2.ui.screen.listaScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.pablojhurtadohidalgo.appi2.data.model.MediaItem
import com.pablojhurtadohidalgo.appi2.data.repositories.RemoteConnection
import kotlinx.coroutines.launch

class ListaViewModel: ViewModel() {
    private val _lista: MutableLiveData<List<MediaItem>> = MutableLiveData()
    val lista: LiveData<List<MediaItem>> = _lista

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    private val _user : MutableLiveData<FirebaseUser> = MutableLiveData()
    val user: LiveData<FirebaseUser> = _user


    init{

        viewModelScope.launch(){
            _progressBar.value=true

            val pokemons = RemoteConnection.service.getPokemons()

            _lista.value = pokemons.results.map{
                val id=it.url.split('/').last{it.isNotEmpty()}.toInt()
                val pokemonsDetail= RemoteConnection.service.getPokemonDetails(id)
                MediaItem(
                    id,
                    it.name,
                    pokemonsDetail.sprites.front_default
                )

            }
            _progressBar.value = false
        }
    }
}