package com.pablojhurtadohidalgo.appi2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pablojhurtadohidalgo.appi2.ui.navegacion.Navegacion
import com.pablojhurtadohidalgo.appi2.ui.theme.NavegacionDetalleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacionDetalleTheme {
                Navegacion()
            }
        }
    }
}