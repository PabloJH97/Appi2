package com.pablojhurtadohidalgo.appi2.ui.screen.gamesScreen

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

class GamesViewModel: ViewModel() {
    private val _lista: MutableLiveData<List<MediaJuego>> = MutableLiveData()
    val lista: LiveData<List<MediaJuego>> = _lista

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    private val _user : MutableLiveData<FirebaseUser> = MutableLiveData()
    val user: LiveData<FirebaseUser> = _user

    private val firestore = FirebaseFirestore.getInstance()


    init{

        viewModelScope.launch(){
            _progressBar.value=true

            val juegos = getJuegos()

            _lista.value = juegos
            _progressBar.value = false
        }
    }

    private suspend fun getJuegos(): List<MediaJuego> {
        return try{
            val listaJuegos=mutableListOf<MediaJuego>()
            val querySnapshot=firestore.collection("juegos")
                .get()
                .await()
            for(document in querySnapshot.documents){
                val juegoId=document.getString("id")
                val juegoName=document.getString("name")
                val juegoPhoto=document.getString("photo")
                if (juegoId != null) {
                    listaJuegos.add(MediaJuego(
                        id=juegoId.toInt(),
                        name=juegoName,
                        photo=juegoPhoto
                     ))
                }

            }
            listaJuegos
        }catch(e:Exception){
            emptyList()
        }
    }
}