package com.pablojhurtadohidalgo.appi2.data.model

data class PokemonRepo(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)