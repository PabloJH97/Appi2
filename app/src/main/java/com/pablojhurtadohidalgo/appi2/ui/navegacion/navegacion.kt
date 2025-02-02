package com.pablojhurtadohidalgo.appi2.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pablojhurtadohidalgo.appi2.ui.screen.detailScreen.DetailScreen
import com.pablojhurtadohidalgo.appi2.ui.screen.listaScreen.ListaViewModel
import com.pablojhurtadohidalgo.appi2.ui.screen.listaScreen.ListaScreen

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Lista) {
        composable<Lista> {
            val viewModel = ListaViewModel()
            ListaScreen(viewModel) { id ->
                navController.navigate(Detail(id))
            }
        }
        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Detail>()
            val id = detail.id
            DetailScreen(id)
        }
    }
}