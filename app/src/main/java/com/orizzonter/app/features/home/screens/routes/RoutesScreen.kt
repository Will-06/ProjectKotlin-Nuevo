
// Importación de elementos de UI y diseño
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orizzonter.app.R
import com.orizzonter.app.core.navigation.CHAT_ROUTE

@Composable
fun RoutesScreen(navController: NavController) {
    // Definición de bordes y colores
    val cornerRadius = 24.dp
    val shape = RoundedCornerShape(cornerRadius)
    val onSurfaceAlpha = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.glasssmap2),
            contentDescription = "Mapa de fondo",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(32.dp)),
            contentScale = ContentScale.Crop
        )

        // Campo de búsqueda y botón de chat
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            var searchQuery by remember { mutableStateOf("") }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TextField expandible
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar una ruta...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(shape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                        .border(1.dp, onSurfaceAlpha, shape)
                        .padding(horizontal = 4.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = onBackgroundColor.copy(alpha = 0.5f),
                        unfocusedPlaceholderColor = onBackgroundColor.copy(alpha = 0.5f),
                        focusedTextColor = onBackgroundColor,
                        unfocusedTextColor = onBackgroundColor
                    ),
                    shape = shape,
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

// Botón de chat con nuevo color
                IconButton(
                    onClick = {
                        navController.navigate(CHAT_ROUTE) // Navega al chat
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                        .border(1.dp, onSurfaceAlpha, RoundedCornerShape(14.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ia),
                        contentDescription = "Chat",
                        tint = MaterialTheme.colorScheme.primary // Nuevo color
                    )
                }

            }
        }

        // Mensaje informativo en el centro
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(70.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f), shape)
                    .padding(23.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Rutas",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = onBackgroundColor
                )

                Spacer(modifier = Modifier.height(17.dp))

                Text(
                    text = "Próximamente podrás consultar tus rutas desde aquí.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        lineHeight = 24.sp
                    ),
                    color = onBackgroundColor,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}