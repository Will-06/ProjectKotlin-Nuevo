package com.orizzonter.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.orizzonter.app.core.designsystem.AppTheme
import com.orizzonter.app.core.designsystem.LocalAppTheme
import com.orizzonter.app.core.designsystem.OrizzonterTheme
import com.orizzonter.app.core.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val systemDarkTheme = isSystemInDarkTheme()  // llama directo, sin remember

            var isDarkTheme by remember { mutableStateOf(systemDarkTheme) }

            OrizzonterTheme(darkTheme = isDarkTheme) {
                CompositionLocalProvider(
                    LocalAppTheme provides AppTheme(
                        isDark = isDarkTheme,
                        toggleTheme = { isDarkTheme = !isDarkTheme }
                    )
                ) {
                    AppNavGraph()
                }
            }
        }

    }
}
