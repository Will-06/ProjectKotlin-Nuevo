 package com.orizzonter.app.features.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orizzonter.app.features.home.components.BottomBar
import com.orizzonter.app.features.home.screens.community.CommunityScreen
import com.orizzonter.app.features.home.screens.routes.RoutesScreen
import com.orizzonter.app.features.home.screens.services.ServicesScreen
import com.orizzonter.app.features.home.screens.settings.SettingsScreen
import com.orizzonter.app.features.auth.data.AuthPreferences
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onLogoutSuccess: () -> Unit  // callback para que AppNavGraph pueda reaccionar al logout
) {
    val context = LocalContext.current
    val authPreferences = remember { AuthPreferences(context) }

    val navController = rememberNavController()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "routes",
            modifier = Modifier.padding(padding)
        ) {
            composable("routes") { RoutesScreen() }
            composable("services") { ServicesScreen() }
            composable("social") { CommunityScreen() }
            composable("settings") {
                SettingsScreen(
                    authPreferences = authPreferences,
                    onLogout = {
                        coroutineScope.launch {
                            authPreferences.logout()
                            // Navegar fuera de home al login
                            onLogoutSuccess()
                        }
                    }
                )
            }
        }
    }
}
