package com.pablojhurtadohidalgo.appi2.data.model

import com.pablojhurtadohidalgo.appi2.data.model.pokemon.PokemonDetailRepo

data class PokeData(
    val usuario: String?="",
    val pokemon: String="",
    val pokedata: PokemonDetailRepo?
)
