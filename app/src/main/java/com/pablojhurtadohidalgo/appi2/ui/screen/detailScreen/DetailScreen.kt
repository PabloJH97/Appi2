package com.pablojhurtadohidalgo.appi2.ui.screen.detailScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.pablojhurtadohidalgo.appi2.data.AuthManager
import com.pablojhurtadohidalgo.appi2.data.FirestoreManager
import com.pablojhurtadohidalgo.appi2.data.model.MediaItem
import com.pablojhurtadohidalgo.appi2.data.model.PokeData
import com.pablojhurtadohidalgo.appi2.data.model.PokemonData

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(auth: AuthManager, viewModel: DetailViewModel, pokemonId: Int, firestore: FirestoreManager){
    val pokemonDetails by viewModel.pokemonDetails.observeAsState()
    val progressBar by viewModel.progressBar.observeAsState(false)
    val factory = DetailViewModelFactory(firestore, pokemonId)
    val pokeList= mutableListOf<PokemonData>()
    val user = auth.getCurrentUser()
    val detailViewModel = viewModel(DetailViewModel::class.java, factory=factory)
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick={detailViewModel.addPokemon(
                    PokeData(
                        id="",
                        usuario=auth.getCurrentUser()?.uid,
                        pokemon=pokemonId.toString(),
                        pokedata=pokeList
                )
                )}
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",

                    )
            }
        }
    ) {
        if(progressBar){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            pokemonDetails?.let {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Card(
                        modifier=Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape= RoundedCornerShape(8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .width(200.dp)
                                .padding(2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center

                        ) {
                            val context = LocalContext.current
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            ) {

                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = it.sprites.front_default,
                                        imageLoader = ImageLoader.Builder(context).crossfade(true).build()
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.labelLarge,
                                    overflow = TextOverflow.Ellipsis,
                                )

                            }
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = it.types.first().type.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                            if(it.types.size>1){
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = it.types.last().type.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }

                        }

                    }
                }


            }
        }
    }




}