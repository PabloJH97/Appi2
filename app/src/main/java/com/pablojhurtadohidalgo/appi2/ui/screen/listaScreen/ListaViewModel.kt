package com.pablojhurtadohidalgo.appi2.ui.screen.listaScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablojhurtadohidalgo.appi2.data.model.MediaItem
import com.pablojhurtadohidalgo.appi2.data.repositories.RemoteConnection
import kotlinx.coroutines.launch

class ListaViewModel: ViewModel() {
    private val _lista: MutableLiveData<List<MediaItem>> = MutableLiveData()
    val lista: LiveData<List<MediaItem>> = _lista

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    init{
        _progressBar.value=true
        viewModelScope.launch(){
            val pokemons = RemoteConnection.service.getPokemons()
            _lista.value = pokemons.results.map{
                val id=it.url.split('/')
                val pokemonsDetail= RemoteConnection.service.getPokemonDetails(1)
                MediaItem(
                    id.last().toInt(),
                    it.name,
                    pokemonsDetail.sprites.front_default
                )

            }
        }
    }
}