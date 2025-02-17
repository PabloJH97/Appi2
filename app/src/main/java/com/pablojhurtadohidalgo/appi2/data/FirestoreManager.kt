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

class FirestoreManager(auth: AuthManager, context: Context) {
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = auth.getCurrentUser()?.uid

    companion object {
        const val FAVORITOS_COLLECTION = "favoritos"
        const val POKEDATA_COLLECTION = "pokedata"
    }

    fun getPokemons(): Flow<List<PokeData>> {
    return firestore.collection(FAVORITOS_COLLECTION)
        .whereEqualTo("userId", userId)
        .snapshots()
        .map { qs ->
            qs.documents.mapNotNull { ds ->
                ds.toObject(PokedataDB::class.java)?.let{pokedataDB->
                    PokeData(
                        id=ds.id,
                        usuario=pokedataDB.usuario,
                        pokemon=pokedataDB.pokemon,
                        pokedata=pokedataDB.pokedata

                    )
                }
            }
        }
    }
    suspend fun addPokemon(pokeData: PokeData){
        firestore.collection(FAVORITOS_COLLECTION).add(pokeData).await()
    }

    suspend fun deletePokemonById(id: String){
        firestore.collection(FAVORITOS_COLLECTION).document(id).delete().await()
    }

    fun getPokeDataId(id: String): PokeData {
        return firestore.collection(FAVORITOS_COLLECTION).document(id)
            .get().result?.toObject(PokedataDB::class.java)?.let {
                PokeData(
                    id = id,
                    usuario = it.usuario,
                    pokemon = it.pokemon,
                    pokedata = it.pokedata
                )
            }!!
    }


}