package com.pablojhurtadohidalgo.appi2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.pablojhurtadohidalgo.appi2.data.AuthManager
import com.pablojhurtadohidalgo.appi2.ui.navegacion.Navegacion
import com.pablojhurtadohidalgo.appi2.ui.theme.NavegacionDetalleTheme

class MainActivity : ComponentActivity() {
    val auth = AuthManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Firebase.analytics
        setContent {
            NavegacionDetalleTheme {
                Navegacion(auth)
            }
        }

    }
    override fun onDestroy(){
        super.onDestroy()
        auth.signOut()
    }
}