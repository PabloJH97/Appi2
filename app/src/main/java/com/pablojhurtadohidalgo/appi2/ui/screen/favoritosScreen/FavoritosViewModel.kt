package com.pablojhurtadohidalgo.appi2.ui.screen.favoritosScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pablojhurtadohidalgo.appi2.data.FirestoreManager
import com.pablojhurtadohidalgo.appi2.data.model.MediaItem
import com.pablojhurtadohidalgo.appi2.data.model.PokeData
import com.pablojhurtadohidalgo.appi2.data.model.PokedataDB
import com.pablojhurtadohidalgo.appi2.data.repositories.RemoteConnection
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FavoritosViewModel: ViewModel() {
    private val _lista: MutableLiveData<List<MediaItem>> = MutableLiveData()
    val lista: LiveData<List<MediaItem>> = _lista

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    private val _user : MutableLiveData<FirebaseUser?> = MutableLiveData()
    val user: MutableLiveData<FirebaseUser?> = _user

    private val firestore = FirebaseFirestore.getInstance()


    init{

        viewModelScope.launch(){
            _progressBar.value=true

            val currentUser= FirebaseAuth.getInstance().currentUser
            _user.value = currentUser

            if(currentUser!=null){
                val userPokemonIds=getUserPokemonIds(currentUser.uid)

                val pokemons=RemoteConnection.service.getPokemons()

                val filteredPokemons=pokemons.results.filter{pokemon ->
                    val id=pokemon.url.split('/').last{it.isNotEmpty()}.toInt()
                    userPokemonIds.contains(id)
                }


            _lista.value = filteredPokemons.map{pokemon->
                val id=pokemon.url.split('/').last{it.isNotEmpty()}.toInt()
                val pokemonsDetail= RemoteConnection.service.getPokemonDetails(id)
                MediaItem(
                    id,
                    pokemon.name,
                    pokemonsDetail.sprites.front_default
                )

            }
            _progressBar.value = false
            }
        }
    }

    private suspend fun getUserPokemonIds(userId: String): List<Int> {
        return try{
            val pokemonIds=mutableListOf<Int>()
            val querySnapshot=firestore.collection("favoritos")
                .whereEqualTo("usuario", userId)
                .get()
                .await()
            for(document in querySnapshot.documents){
                val pokemonId=document.getString("pokemon")
                if(pokemonId!=null){
                    pokemonIds.add(pokemonId.toInt())
                }
            }
            pokemonIds
        }catch(e:Exception){
            emptyList()
        }
    }
}