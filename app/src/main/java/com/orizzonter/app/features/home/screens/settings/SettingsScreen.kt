package com.orizzonter.app.features.home.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orizzonter.app.R
import com.orizzonter.app.core.designsystem.LocalAppTheme

@Composable
fun SettingsScreen(onLogout: () -> Unit = {}) {
    val theme = LocalAppTheme.current
    val scrollState = rememberScrollState()  // ScrollState para scroll vertical

    Column(
        modifier = Modifier
            .fillMaxSize()
            // Solo padding horizontal y superior, sin padding inferior
            .padding(horizontal = 24.dp, vertical = 0.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Perfil centrado
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.fotoperfil),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Orizzonter", style = MaterialTheme.typography.bodyLarge)
            Text(
                "orizzonter@gmail.com",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Opciones base
        SettingToggle("Modo oscuro", checked = theme.isDark) { theme.toggleTheme() }
        SettingToggle("Notificaciones", checked = true)
        SettingSelector("Idioma", selected = "Español")

        Spacer(modifier = Modifier.height(32.dp))

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text("Preferencias de Ruta", style = MaterialTheme.typography.titleSmall)
        SettingSelector("Tipo de ciclismo", selected = "Montaña")
        SettingSelector("Nivel de dificultad", selected = "Moderado")

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text("Comunidad", style = MaterialTheme.typography.titleSmall)
        SettingToggle("Mostrar mi perfil en rutas públicas", checked = true)
        SettingToggle("Permitir que otros me sigan", checked = true)

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text("Alertas de Seguridad", style = MaterialTheme.typography.titleSmall)
        SettingToggle("Alertas de clima extremo", checked = false)
        SettingToggle("Notificar sobre rutas peligrosas", checked = true)

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text("Mapa", style = MaterialTheme.typography.titleSmall)
        SettingSelector("Estilo del mapa", selected = "Oscuro")
        SettingToggle("Mostrar talleres automáticamente", checked = true)

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text("Privacidad", style = MaterialTheme.typography.titleSmall)
        SettingToggle("Compartir ubicación en tiempo real", checked = false)
        SettingToggle("Guardar historial de rutas", checked = true)

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de cerrar sesión
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Composable
fun SettingToggle(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingSelector(title: String, selected: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(title)
            Text(
                selected,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
    }
}
