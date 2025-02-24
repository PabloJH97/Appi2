package com.pablojhurtadohidalgo.appi2.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pablojhurtadohidalgo.appi2.data.AuthManager
import com.pablojhurtadohidalgo.appi2.data.FirestoreManager
import com.pablojhurtadohidalgo.appi2.ui.screen.detailScreen.DetailScreen
import com.pablojhurtadohidalgo.appi2.ui.screen.detailScreen.DetailViewModel
import com.pablojhurtadohidalgo.appi2.ui.screen.listaScreen.ListaViewModel
import com.pablojhurtadohidalgo.appi2.ui.screen.listaScreen.ListaScreen
import com.pablojhurtadohidalgo.appi2.ui.screen.authScreen.LoginScreen
import com.pablojhurtadohidalgo.appi2.ui.screen.authScreen.ForgotPasswordScreen
import com.pablojhurtadohidalgo.appi2.ui.screen.authScreen.SignUpScreen
import com.pablojhurtadohidalgo.appi2.ui.screen.detailGameScreen.DetailGameScreen
import com.pablojhurtadohidalgo.appi2.ui.screen.detailGameScreen.DetailGameViewModel
import com.pablojhurtadohidalgo.appi2.ui.screen.favoritosScreen.FavoritosScreen
import com.pablojhurtadohidalgo.appi2.ui.screen.favoritosScreen.FavoritosViewModel
import com.pablojhurtadohidalgo.appi2.ui.screen.gamesScreen.GamesScreen
import com.pablojhurtadohidalgo.appi2.ui.screen.gamesScreen.GamesViewModel

@Composable
fun Navegacion(auth: AuthManager) {
    val navController = rememberNavController()
    val context= LocalContext.current
    val firestore = FirestoreManager(auth, context)

    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            LoginScreen(
                auth,
                { navController.navigate(SignUp) },
                {
                    navController.navigate(Lista) {
                        popUpTo(Login) { inclusive = true }
                    }
                },
                { navController.navigate(ForgotPassword) }
            )
        }

        composable<SignUp> {
            SignUpScreen(
                auth
            ) { navController.popBackStack() }
        }

        composable<Lista>{
            val viewModel= ListaViewModel()
            ListaScreen(auth, viewModel, navigateToLogin={navController.navigate(Login){popUpTo(Lista){inclusive=true} } }, navigateToFavoritos = { navController.navigate(Favoritos)}, navigateToJuegos = { navController.navigate(Games) }){
                id->navController.navigate(Detail(id))

            }
        }

        composable<Games>{
            val viewModel= GamesViewModel()
            GamesScreen(auth, viewModel, navigateToLogin={navController.navigate(Login){popUpTo(Lista){inclusive=true} } }){
                    name->navController.navigate(DetailGame(name))

            }
        }

        composable<DetailGame> { backStackEntry ->
            val detail= backStackEntry.toRoute<DetailGame>()
            val name= detail.name
            val viewModel= DetailGameViewModel(name)
            DetailGameScreen(auth, viewModel, navigateToLogin={navController.navigate(Login){popUpTo(Lista){inclusive=true} } }){
                    id->navController.navigate(Detail(id))
            }
        }

        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Detail>()
            val id = detail.id
            val viewModel= DetailViewModel(firestore, id)
            DetailScreen(auth, viewModel, id, firestore)
        }

        composable<Favoritos> {
            val viewModel=FavoritosViewModel()
            FavoritosScreen(auth, viewModel, navigateToLogin={navController.navigate(Login){popUpTo(Lista){inclusive=true} } }){
                    id->navController.navigate(Detail(id))
        }

        composable <ForgotPassword> {
            ForgotPasswordScreen(
                auth
            ) { navController.navigate(Login) {
                popUpTo(Login){ inclusive = true }
            } }
        }
    }
}
}