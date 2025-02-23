package com.pablojhurtadohidalgo.appi2.ui.screen.favoritosScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.pablojhurtadohidalgo.appi2.R
import com.pablojhurtadohidalgo.appi2.data.AuthManager
import com.pablojhurtadohidalgo.appi2.data.model.MediaItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(auth: AuthManager, viewModel: FavoritosViewModel, navigateToLogin: () -> Unit, navigateToDetail: (Int) -> Unit){
    val lista by viewModel.lista.observeAsState(emptyList())
    val progressBar by viewModel.progressBar.observeAsState(false)
    var showDialog by remember { mutableStateOf(false) }
    val user =auth.getCurrentUser()

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(user?.photoUrl != null){
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(user?.photoUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Imagen",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(40.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.profile),
                                contentDescription = "Foto de perfil por defecto",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )

                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = user?.displayName ?: "Anónimo",
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text =  user?.email ?: "Sin correo",
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Cyan),
                actions = {
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(Icons.AutoMirrored.Outlined.ExitToApp, contentDescription = "Cerrar sesión")
                    }
                }
            )
        }
    ){
        Box(
            modifier = Modifier.fillMaxSize().padding(it)
        ){
            if (showDialog){
                LogoutDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        auth.signOut()
                        navigateToLogin()
                        showDialog = false
                    }
                )
            }
            if(progressBar) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {


                if (lista!!.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay elementos", style = MaterialTheme.typography.bodySmall)
                    }
                } else {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        //modifier = Modifier.fillMaxSize()
                    ) {
                        items(lista!!) { mediaItem ->
                            MediaListItem(mediaItem, navigateToDetail)
                        }
                    }
                }
            }
        }
    }



}

@Composable
private fun MediaListItem(mediaItem: MediaItem, navigateToDetail: (Int) -> Unit){
    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(2.dp)
            .clickable { navigateToDetail(mediaItem.id) },
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Imagen(item = mediaItem)
        Title(item = mediaItem)
    }
}

@Composable
fun Imagen(item: MediaItem, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {

        Image(
            painter = rememberAsyncImagePainter(
                model = item.photo,
                imageLoader = ImageLoader.Builder(context).crossfade(true).build()
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun Title(item: MediaItem) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Cyan)
            .padding(16.dp)
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.labelLarge,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun LogoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cerrar Sesión") },
        text = {
            Text("¿Estás seguro de que deseas cerrar sesión?")
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}