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
    primary = Color(0xFF005187),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF4D82BC),
    onSecondary = Color(0xFFFFFFFF),
    tertiary = Color(0xFF84B6F4),
    onTertiary = Color(0xFF000000),
    background = Color(0xFFFCFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFDDE9F5), //Navigation bar
    onSurface = Color(0xFF000000),
    primaryContainer = Color(0xFFD3E6F9),     // claro pastel del primary
    onPrimaryContainer = Color(0xFF000000),
    secondaryContainer = Color(0xFFDDE9F5),   // variante suave del secondary
    onSecondaryContainer = Color(0xFF000000),
    error = Color(0xFFFF2638)                  // acento cálido tipo mostaza/naranja
)



private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF005187),         // tu azul profundo
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF4D82BC),       // tu azul medio
    onSecondary = Color(0xFFFFFFFF),
    tertiary = Color(0xFF84B6F4),        // azul claro
    onTertiary = Color(0xFF000000),
    background = Color(0xFF102A43),      // inspirado en blue dark mode
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF163B5C),         // un pelín más claro
    onSurface = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF274765),// tono intermedio
    onPrimaryContainer = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFF385A75),
    onSecondaryContainer = Color(0xFFFFFFFF),
    error = Color(0xFFFFA726)
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
