package com.pablojhurtadohidalgo.appi2.data.model

import com.pablojhurtadohidalgo.appi2.data.model.pokemon.Ability
import com.pablojhurtadohidalgo.appi2.data.model.pokemon.Move
import com.pablojhurtadohidalgo.appi2.data.model.pokemon.Type

data class PokemonData(
    val id: Int,
    val name: String,
    val abilities: List<Ability>,
    val types: List<Type>,
    val moves: List<Move>,
)