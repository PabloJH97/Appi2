package com.pablojhurtadohidalgo.appi2.data.repositories


import com.pablojhurtadohidalgo.appi2.data.model.PokemonRepo
import com.pablojhurtadohidalgo.appi2.data.model.pokemon.PokemonDetailRepo
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteService {
    @GET("pokemon")
    suspend fun getPokemons():PokemonRepo

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): PokemonDetailRepo
}