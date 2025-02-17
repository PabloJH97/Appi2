package com.pablojhurtadohidalgo.appi2.data.model

data class PokeData(
    var id: String?="",
    val usuario: String?="",
    val pokemon: String="",
    val pokedata: Collection<PokemonData>
)
