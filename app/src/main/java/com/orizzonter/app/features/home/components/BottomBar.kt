package com.orizzonter.app.features.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.orizzonter.app.R
import androidx.compose.foundation.layout.size

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector? = null,
    @DrawableRes val drawableRes: Int? = null
) {
    data object Routes : BottomNavItem("routes", "Rutas", drawableRes = R.drawable.cursor)
    data object Services : BottomNavItem("services", "Servicios", drawableRes = R.drawable.handshake)
    data object Social : BottomNavItem("social", "Comunidad", drawableRes = R.drawable.community)
    data object Settings : BottomNavItem("settings", "Ajustes", drawableRes = R.drawable.settings)
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Routes,
        BottomNavItem.Services,
        BottomNavItem.Social,
        BottomNavItem.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        item.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        } ?: item.drawableRes?.let {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
