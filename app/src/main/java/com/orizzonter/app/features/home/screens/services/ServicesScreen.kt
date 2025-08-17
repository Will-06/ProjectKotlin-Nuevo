package com.orizzonter.app.features.home.screens.services

// Importación de componentes de UI y diseño
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ServicesScreen(navController: NavController) {
    // Estructura principal de la pantalla
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()), // Permite scroll vertical
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Header() // Título y descripción superior

            Spacer(Modifier.height(32.dp))

            ServiceList(navController) // Lista de servicios disponibles

            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun Header() {
    // Encabezado con texto informativo
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Consulta los servicios disponibles para que tu experiencia sea única.",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                lineHeight = 22.sp
            ),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth(0.85f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ServiceList(navController: NavController) {
    // Lista de servicios con título, descripción e ícono
    val services = listOf(
        Triple("Taller de Bicis", "Repara y mantén tu bicicleta con los mejores expertos.", Icons.Default.Build),
        Triple("Rutas Guiadas", "Explora rutas seguras y emocionantes con acompañamiento profesional.", Icons.Default.Map),
        Triple("Alquiler de Bicicletas", "Encuentra bicicletas disponibles para cualquier tipo de aventura.", Icons.Default.DirectionsBike),
        Triple("Tiendas Especializadas", "Compra accesorios y equipo de calidad para tu ciclismo.", Icons.Default.Storefront),
        Triple("Consejos de Salud", "Información sobre nutrición, cuidado y bienestar para ciclistas.", Icons.Default.HealthAndSafety),
        Triple("Puntos de Hidratación", "Localiza estaciones para hidratarte durante tus rutas.", Icons.Default.LocalCafe),
        // Nuevos servicios agregados
        Triple("Cicloturismo", "Descubre experiencias turísticas en bicicleta por lugares emblemáticos.", Icons.Default.Explore),
        Triple("Taller Móvil", "Servicio de reparación que viene a tu ubicación cuando lo necesites.", Icons.Default.MiscellaneousServices),
        Triple("Eventos Ciclísticos", "Participa en competencias y reuniones para ciclistas.", Icons.Default.Event),
        Triple("Seguros para Bicis", "Protege tu bicicleta contra robos y daños accidentales.", Icons.Default.Security),
        Triple("Estacionamientos Seguros", "Lugares vigilados para dejar tu bicicleta con tranquilidad.", Icons.Default.Lock),
        Triple("Clínicas de Mantenimiento", "Talleres periódicos para mantener tu bici en óptimas condiciones.", Icons.Default.Handyman),
        Triple("Alquiler de Equipos", "Cascos, luces y otros accesorios disponibles para rentar.", Icons.Default.Devices),
        Triple("Rutas Nocturnas", "Recorridos grupales con iluminación y seguridad para ciclistas.", Icons.Default.Nightlight),
        Triple("Asesoría Personalizada", "Expertos que te ayudan a elegir la bicicleta perfecta para ti.", Icons.Default.PersonSearch),
        Triple("Comunidad Ciclista", "Conéctate con otros entusiastas del ciclismo en tu área.", Icons.Default.Group)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp) // Espaciado entre tarjetas
    ) {
        services.forEach { (title, description, icon) ->
            ServiceMiniCard(title, description, icon) { // Pasamos el navController
                // Navegar a la pantalla de detalles
                navController.navigate("serviceDetail/${title}/${description}")
            }
        }
    }
}

@Composable
fun ServiceMiniCard(title: String, description: String, icon: ImageVector, onClick: () -> Unit) {
    // Tarjeta individual de servicio
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
            .clickable { onClick() },  // Agregar la acción de clic
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono del servicio
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(Modifier.width(16.dp))

        // Título y descripción del servicio
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    lineHeight = 18.sp
                ),
                maxLines = 3 // Limita la descripción a 3 líneas
            )
        }
    }
}
