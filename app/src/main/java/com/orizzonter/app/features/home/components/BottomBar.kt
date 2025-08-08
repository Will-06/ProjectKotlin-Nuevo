package com.orizzonter.app.features.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color

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
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                shape = RoundedCornerShape(24.dp)
            ),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.15f),
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(24.dp),
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route
                NavigationBarItem(
                    icon = {
                        item.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp),
                                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        } ?: item.drawableRes?.let {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp),
                                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )
                )
            }
        }
    }
}
