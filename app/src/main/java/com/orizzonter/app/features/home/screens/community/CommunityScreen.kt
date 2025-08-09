package com.orizzonter.app.features.home.screens.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orizzonter.app.R

@Composable
fun CommunityScreen() {
    val communityPosts = listOf(
        CommunityPost(
            user = "Andrés Velasco",
            avatarResId = R.drawable.avatar,
            text = "¿Alguien ha probado una de las nuevas rutas rurales que suben por Santa Rosa o Las Guacas? Vi que están marcadas como dificultad media 🔥",
            likes = 14,
            comments = 5,
            imageResId = R.drawable.popayan
        ),

        CommunityPost(
            user = "Laura Fernández",
            avatarResId = R.drawable.avatarmujer,
            text = "Sí, fui el domingo por el sector de Las Guacas. Tiene una subida fuerte al inicio, pero la vista de Popayán desde arriba vale totalmente la pena . Lleven agua 💧",
            likes = 11,
            comments = 2,
            imageResId = null
        ),

        CommunityPost(
            user = "Carlos Méndez",
            avatarResId = R.drawable.avatar,
            text = "Recomiendo revisar la presión de las llantas antes. Cerca al parque de La Paz hay una tienda que ajusta gratis. La encontré por Oizzonter 🚲🔧",
            likes = 9,
            comments = 1,
            imageResId = R.drawable.monserrate
        ),

        CommunityPost(
            user = "Andrés Velasco",
            avatarResId = R.drawable.avatar,
            text = "Gracias por los datos! ¿Qué tal si armamos un recorrido grupal para este sábado y probamos juntos esa ruta? 🚴‍♀️🌿",
            likes = 17,
            comments = 6,
            imageResId = null
        ),

        CommunityPost(
            user = "Laura Fernández",
            avatarResId = R.drawable.avatarmujer,
            text = "¡Me apunto! Puedo marcar la ruta en la app y compartir el punto de encuentro. ¿8:30am en la glorieta norte les sirve?",
            likes = 15,
            comments = 4,
            imageResId = null
        ),

        CommunityPost(
            user = "Orizzonter Team",
            avatarResId = R.drawable.logo_orizzonter,
            text = "¡Nos encanta ver cómo se conectan! Recuerden que pueden registrar su ruta, compartirla con otros y calificarla. ¡Exploren nuevos Horizontes! 💚",
            likes = 25,
            comments = 10,
            imageResId = R.drawable.logo_orizzonter
        )

    )

    Scaffold(
        floatingActionButton = {
            Box(modifier = Modifier.padding(bottom = 100.dp)) {
                FloatingActionButton(
                    onClick = { /* Acción para nueva publicación */ },
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    contentColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Publicar")
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Respeta la NavigationBar
                .padding(horizontal = 24.dp), // Espaciado lateral
            contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(communityPosts) { post ->
                CommunityPostCard(post)
            }
        }
    }
}

data class CommunityPost(
    val user: String,
    val avatarResId: Int,
    val text: String,
    val likes: Int,
    val comments: Int,
    val imageResId: Int?
)

@Composable
fun CommunityPostCard(post: CommunityPost) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                shape = RoundedCornerShape(24.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = post.avatarResId),
                    contentDescription = "Avatar de ${post.user}",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = post.user,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = "Hace 2 horas",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                    )
                }
            }

            post.imageResId?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Imagen de publicación",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = post.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = "Me gusta",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${post.likes}", color = MaterialTheme.colorScheme.onBackground)

                Spacer(modifier = Modifier.width(24.dp))

                Icon(
                    Icons.Default.ChatBubbleOutline,
                    contentDescription = "Comentarios",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${post.comments}", color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}
