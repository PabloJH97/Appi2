package com.pablojhurtadohidalgo.appi2.ui.navegacion

import kotlinx.serialization.Serializable

@Serializable
object Lista

@Serializable
data class Detail(val id: Int)

@Serializable
object Login

@Serializable
object SignUp

@Serializable
object ForgotPassword

@Serializable
object Favoritos