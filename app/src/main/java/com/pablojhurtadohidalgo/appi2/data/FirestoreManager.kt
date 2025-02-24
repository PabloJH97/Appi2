package com.pablojhurtadohidalgo.appi2.data

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.pablojhurtadohidalgo.appi2.data.model.PokeData
import com.pablojhurtadohidalgo.appi2.data.model.PokedataDB
import com.pablojhurtadohidalgo.appi2.data.model.PokemonRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.lang.Boolean.FALSE

class FirestoreManager(auth: AuthManager, context: Context) {

    private val firestore = FirebaseFirestore.getInstance()
    private val userId = auth.getCurrentUser()?.uid

    companion object {
        const val FAVORITOS_COLLECTION = "favoritos"
        const val JUEGOS_COLLECTION = "juegos"
    }

    fun getPokemons(): Flow<List<PokeData>> {
    return firestore.collection(FAVORITOS_COLLECTION)
        .whereEqualTo("userId", userId)
        .snapshots()
        .map { qs ->
            qs.documents.mapNotNull { ds ->
                ds.toObject(PokedataDB::class.java)?.let{pokedataDB->
                    PokeData(
                        usuario=pokedataDB.usuario,
                        pokemon=pokedataDB.pokemon,
                        pokedata=pokedataDB.pokedata

                    )
                }
            }
        }
    }

    suspend fun addPokemon(pokeData: PokeData){
        val pokeDB=firestore.collection(FAVORITOS_COLLECTION)
        pokeDB
            .whereEqualTo("usuario", pokeData.usuario)
            .whereEqualTo("pokemon", pokeData.pokemon)
            .get()
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    if(task.result?.isEmpty==true){
                        val newPokemon=hashMapOf(
                            "usuario" to pokeData.usuario,
                            "pokemon" to pokeData.pokemon,
                            "pokedata" to pokeData.pokedata
                        )
                        pokeDB.add(newPokemon)
                    }else{
                        for(document in task.result!!){
                            document.reference.delete()
                        }
                    }
                }
            }
//        firestore.collection(FAVORITOS_COLLECTION).add(pokeData).await()
    }

    suspend fun deletePokemon(pokeData: PokeData){
        val pokeDB=firestore.collection(FAVORITOS_COLLECTION)
        pokeDB
            .whereEqualTo("usuario", pokeData.usuario)
            .whereEqualTo("pokemon", pokeData.pokemon)
            .get()
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    for(document in task.result!!){
                        document.reference.delete()
                    }
                }
            }
    }

    fun dataExists(pokeData: PokeData, callback: (Boolean)->Unit)
    {
        val pokeDB=firestore.collection(FAVORITOS_COLLECTION)
        pokeDB
            .whereEqualTo("usuario", pokeData.usuario)
            .whereEqualTo("pokemon", pokeData.pokemon)
            .get()
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    callback(!task.result!!.isEmpty)
                }else{
                    callback(false)
                }
            }
    }

    fun getPokeDataId(id: String): PokeData {
        return firestore.collection(FAVORITOS_COLLECTION).document(id)
            .get().result?.toObject(PokedataDB::class.java)?.let {
                PokeData(
                    usuario = it.usuario,
                    pokemon = it.pokemon,
                    pokedata = it.pokedata
                )
            }!!
    }


}