package com.orizzonter.app.features.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.orizzonter.app.R

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector? = null,
    @DrawableRes val drawableRes: Int? = null,
    val title: String
) {
    data object Routes : BottomNavItem("routes", null, R.drawable.cursor, "Rutas")
    data object Services : BottomNavItem("services", null, R.drawable.handshake, "Servicios")
    data object Social : BottomNavItem("social", null, R.drawable.community, "Comunidad")
    data object Settings : BottomNavItem("settings", null, R.drawable.settings, "Ajustes")
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items: List<BottomNavItem> = listOf(
        BottomNavItem.Routes,
        BottomNavItem.Services,
        BottomNavItem.Social,
        BottomNavItem.Settings
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 15.dp),
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        item.icon?.let {
                            Icon(it, contentDescription = item.title)
                        } ?: item.drawableRes?.let {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = item.title
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
