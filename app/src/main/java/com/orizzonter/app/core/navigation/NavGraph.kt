package com.orizzonter.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orizzonter.app.features.auth.*
import com.orizzonter.app.features.home.HomeScreen
import com.orizzonter.app.features.onboarding.OnboardingScreen
import com.orizzonter.app.features.splash.SplashScreen


const val CHAT_ROUTE = "chat" // ✅ Ruta del chat

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("create_account") { CreateAccountScreen(navController) }

        composable("home") {
            HomeScreen(onLogoutSuccess = {
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            })
        }

        //  Ruta al chat
       // composable(CHAT_ROUTE) {
           // ChatScreen(navController) // O solo ChatScreen() si no usa navController
       // }
    }
}
