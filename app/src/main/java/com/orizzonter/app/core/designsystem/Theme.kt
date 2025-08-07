package com.orizzonter.app.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography

val Typography = Typography()

data class AppTheme(
    val isDark: Boolean,
    val toggleTheme: () -> Unit
)

val LocalAppTheme = staticCompositionLocalOf<AppTheme> {
    error("No AppTheme provided")
}

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6EA8E0),             // Azul claro, fresco y brillante
    onPrimary = Color(0xFF1B1B1B),
    secondary = Color(0xFF9CC4E4),           // Azul pastel suave
    onSecondary = Color(0xFF1B1B1B),
    tertiary = Color(0xFFC6DFF6),            // Azul muy claro, casi blanco azulado
    onTertiary = Color(0xFF1B1B1B),
    background = Color(0xFFF0F6FA),          // Blanco con toque azul muy sutil
    onBackground = Color(0xFF1B1B1B),
    surface = Color(0xFFE6F0FA),             // Fondo azul claro mate para superficies
    onSurface = Color(0xFF1B1B1B),
    primaryContainer = Color(0xFFCCE3FA),    // Azul pastel muy claro
    onPrimaryContainer = Color(0xFF19508F),
    secondaryContainer = Color(0xFFD9E8FB),  // Azul muy suave para contenedores
    onSecondaryContainer = Color(0xFF19508F),
    error = Color(0xFFE57373)                 // Rojo suave para acentos
)




private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8A8F94),             // Gris medio azulado, sobrio
    onPrimary = Color(0xFFE0E0E0),
    secondary = Color(0xFFADB5BC),           // Gris claro frío
    onSecondary = Color(0xFFE0E0E0),
    tertiary = Color(0xFFC4CDD2),            // Gris muy claro
    onTertiary = Color(0xFF222222),
    background = Color(0xFF121619),          // Negro mate profundo
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF262B30),             // Gris oscuro mate
    onSurface = Color(0xFFE0E0E0),
    primaryContainer = Color(0xFF3A4045),
    onPrimaryContainer = Color(0xFFE0E0E0),
    secondaryContainer = Color(0xFF4B5257),
    onSecondaryContainer = Color(0xFFE0E0E0),
    error = Color(0xFFFF8A80)                // Rojo pastel para acentos
)



@Composable
fun OrizzonterTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}