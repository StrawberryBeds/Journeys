package com.samuelwood.journeys

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.samuelwood.journeys.viewModels.ViewModelMap
import com.samuelwood.journeys.views.JourneysView
import com.samuelwood.journeys.views.MapScreen
import com.samuelwood.journeys.views.MapScreen
import com.samuelwood.journeys.views.SettingsView



class MainActivity : ComponentActivity() {

    private val permissionGranted = mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted.value = isGranted
        if (!isGranted) {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check and request permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            permissionGranted.value = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        setContent {
            JourneysApp(permissionGranted.value)
        }
    }
}

@Composable
fun JourneysApp(permissionGranted: Boolean) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        NavigationGraph(navController = navController, permissionGranted = permissionGranted)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Journeys,
        BottomNavItem.Map,
        BottomNavItem.Settings
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, permissionGranted: Boolean) {
    NavHost(navController, startDestination = BottomNavItem.Journeys.route) {
        composable(BottomNavItem.Journeys.route) { JourneysView() }
        composable(BottomNavItem.Map.route) { MapScreen(permissionGranted) }
        composable(BottomNavItem.Settings.route) { SettingsView() }
    }
}

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {
    object Journeys : BottomNavItem("Journeys", Icons.Default.Menu, "journeys_screen")
    object Map : BottomNavItem("Map", Icons.Default.Place, "map_screen")
    object Settings : BottomNavItem("Settings", Icons.Default.Settings, "settings_screen")
}


//            NavHost(
//                navController = navController,
//                startDestination = "journeys",
//                modifier = Modifier.padding(16.dp)
//            ) {
//                composable("journeys_screen") {
//                    JourneysView(navController = navController) // Your JourneysView composable
//                }
//                composable("map_screen") {
//                    MapView(navController = navController) // Another composable destination
//                }
//                // Add more composable destinations here
//                composable("settings_screen") {
//                    SettingsView(navController = navController)
//                }
//            }
//        }
//    }
//}
