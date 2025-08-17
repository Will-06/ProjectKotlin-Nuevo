package com.orizzonter.app.features.home.screens.services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.draw.clip
import com.orizzonter.app.R // Asegúrate de importar tu R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailScreen(
    title: String,
    description: String,
    navController: NavController
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                contentColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier.padding(bottom = 100.dp)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                // Reemplazamos el Icon por un Image con tu recurso drawable
                Image(
                    painter = painterResource(id = R.drawable.services), // Cambia "tu_imagen" por el nombre de tu recurso
                    contentDescription = "Imagen del servicio",
                    modifier = Modifier
                        .size(190.dp) // Tamaño ajustable según necesites
                        .clip(RoundedCornerShape(12.dp)), // Opcional: bordes redondeados
                    contentScale = ContentScale.Crop // Ajusta cómo se escala la imagen
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )

            Text("Beneficios:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    "✔ Personal capacitado",
                    "✔ Repuestos originales garantizados",
                    "✔ Servicio rápido disponible"
                ).forEach {
                    Text(text = it, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f))
                }
            }
        }
    }
}