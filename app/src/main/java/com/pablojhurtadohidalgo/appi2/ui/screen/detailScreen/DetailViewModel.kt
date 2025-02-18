package com.pablojhurtadohidalgo.appi2.ui.screen.detailScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pablojhurtadohidalgo.appi2.data.FirestoreManager
import com.pablojhurtadohidalgo.appi2.data.model.PokeData
import com.pablojhurtadohidalgo.appi2.data.model.pokemon.PokemonDetailRepo
import com.pablojhurtadohidalgo.appi2.data.repositories.RemoteConnection
import com.pablojhurtadohidalgo.appi2.data.repositories.RemoteService
import kotlinx.coroutines.launch

class DetailViewModel(val firestoreManager: FirestoreManager, id: Int): ViewModel() {
    private val _pokemonDetails= MutableLiveData<PokemonDetailRepo>()
    val pokemonDetails: LiveData<PokemonDetailRepo> = _pokemonDetails

    private val _progressBar=MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> =_progressBar

    init{
        _progressBar.value=true
        viewModelScope.launch {
            val pokemonDetail = RemoteConnection.service.getPokemonDetails(id)
            _pokemonDetails.value = pokemonDetail
            _progressBar.value=false
        }
    }
    fun addPokemon(pokeData: PokeData){
        viewModelScope.launch {
            firestoreManager.addPokemon(pokeData)
        }
    }

    fun deletePokemon(pokeData: PokeData){
        viewModelScope.launch {
            firestoreManager.deletePokemon(pokeData)
        }
    }



}

class DetailViewModelFactory(private val firestoreManager: FirestoreManager, val id: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(firestoreManager, id) as T
    }
}