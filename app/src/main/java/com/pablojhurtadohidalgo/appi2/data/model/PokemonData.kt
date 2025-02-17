package com.pablojhurtadohidalgo.appi2.data.model

import com.pablojhurtadohidalgo.appi2.data.model.pokemon.Ability
import com.pablojhurtadohidalgo.appi2.data.model.pokemon.Move
import com.pablojhurtadohidalgo.appi2.data.model.pokemon.Sprites
import com.pablojhurtadohidalgo.appi2.data.model.pokemon.Type

data class PokemonData(
    val id: String="",
    val name: String="",
    val types: List<Type>,
    val sprite: String=""
)