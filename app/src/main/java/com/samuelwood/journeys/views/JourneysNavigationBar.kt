package com.samuelwood.journeys.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Journeys : BottomNavItem("journeys_screen", Icons.Filled.Menu, "Journeys")
    object Map : BottomNavItem("map_screen", Icons.Filled.Place, "Map")
    object Settings : BottomNavItem("settings_screen", Icons.Filled.Settings, "Settings")
}

@Composable
fun JourneysNavigationBar(navController: NavController.Companion) {
    NavigationBar() {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null
                )
            },
//            label = {
//                Text(
//                    text = stringResource(R.string.bottom_navigation_home)
//                )
//            },
            selected = true,
            onClick = { navController.navigate("map_screen") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            },
            label = {
//                Text(
//                    text = stringResource(R.string.bottom_navigation_profile)
//                )
            },
            selected = false,
            onClick = { navController.navigate("journeys_screen")}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )
            },
            label = {
//                Text(
//                    text = stringResource(R.string.bottom_navigation_profile)
//                )
            },
            selected = false,
            onClick = { navController.navigate("settings_screen")}
        )
    }
}