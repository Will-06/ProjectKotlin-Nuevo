package com.orizzonter.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orizzonter.app.features.auth.CreateAccountScreen
import com.orizzonter.app.features.auth.ForgotPasswordScreen
import com.orizzonter.app.features.auth.LoginScreen
import com.orizzonter.app.features.home.HomeScreen
import com.orizzonter.app.features.onboarding.OnboardingScreen
import com.orizzonter.app.features.splash.SplashScreen

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
        composable("home") { HomeScreen() }

        // Pantallas de auth
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("create_account") { CreateAccountScreen(navController) }
    }
}