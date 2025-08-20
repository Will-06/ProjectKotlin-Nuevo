package com.orizzonter.app.features.home.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.orizzonter.app.R
import com.orizzonter.app.core.designsystem.LocalAppTheme
import com.orizzonter.app.features.auth.data.AuthPreferences

@Composable
fun SettingsScreen(
    authPreferences: AuthPreferences,
    onLogout: () -> Unit = {}
) {
    val theme = LocalAppTheme.current
    val scrollState = rememberScrollState()

    var userName by remember { mutableStateOf<String?>(null) }
    var userEmail by remember { mutableStateOf<String?>(null) }

    // Cargar datos de usuario cuando la pantalla se monta
    LaunchedEffect(Unit) {
        userName = authPreferences.getUserName()
        userEmail = authPreferences.getUserEmail()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(24.dp))

        // Perfil con nombre y correo dinámicos (sin cambios)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.perfil),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape) // Imagen circular
            )
            Spacer(Modifier.height(8.dp))
            Text(userName ?: "Usuario", style = MaterialTheme.typography.bodyLarge)
            Text(
                userEmail ?: "usuario@correo.com",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Opciones principales de configuración con toggles y selectores
        SettingToggle("Modo oscuro", theme.isDark) { theme.toggleTheme() }
        SettingToggle("Notificaciones", checked = true)
        SettingSelector("Idioma", selected = "Español")

        Spacer(Modifier.height(32.dp))

        // Preferencias específicas de ruta con título mejorado y más espaciado
        SectionTitle("Preferencias de Ruta")
        SettingSelector("Tipo de ciclismo", selected = "Montaña")
        SettingSelector("Nivel de dificultad", selected = "Moderado")

        Spacer(Modifier.height(24.dp))

        // Opciones de comunidad
        SectionTitle("Comunidad")
        SettingToggle("Mostrar mi perfil en rutas públicas", checked = true)
        SettingToggle("Permitir que otros me sigan", checked = true)

        Spacer(Modifier.height(24.dp))

        // Alertas de seguridad
        SectionTitle("Alertas de Seguridad")
        SettingToggle("Notificar sobre rutas peligrosas", checked = true)

        Spacer(Modifier.height(24.dp))

        // Opciones de mapa
        SectionTitle("Mapa")
        SettingToggle("Mostrar talleres automáticamente", checked = true)

        Spacer(Modifier.height(24.dp))

        // Privacidad
        SectionTitle("Privacidad")
        SettingToggle("Guardar historial de rutas", checked = true)

        Spacer(Modifier.height(32.dp))

        // Botón para cerrar sesión con borde y color sutil
        Button(
            onClick = onLogout,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.6f)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.15f),
                contentColor = MaterialTheme.colorScheme.error
            ),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
            )
        ) {
            Text("Cerrar sesión", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(10.dp))
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(bottom = 12.dp)
    )
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
            .padding(vertical = 8.dp)
            .background(
                MaterialTheme.colorScheme.surface.copy(alpha = 0.15f),
                RoundedCornerShape(16.dp)
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = MaterialTheme.colorScheme.onBackground)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        )
    }
}

@Composable
fun SettingSelector(title: String, selected: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(
                MaterialTheme.colorScheme.surface.copy(alpha = 0.12f),
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(2.dp))
            Text(
                selected,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        Icon(
            Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
