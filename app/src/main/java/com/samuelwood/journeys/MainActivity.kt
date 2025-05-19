package com.samuelwood.journeys

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
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
import com.samuelwood.journeys.views.MapView
import com.samuelwood.journeys.views.SettingsView


class MainActivity : ComponentActivity() {

    private val viewModelMap: ViewModelMap by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            findMyLocation()
            Log.i("Message", "findMyLocation called, MainActivity 61")
        } else {
            Log.e("MainActivity", "Location permission denied")
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    private val viewModelMap: ViewModelMap by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            )
        } else {
            findMyLocation()
            Log.i("Message", "findMyLocation called, MainActivity 91")
        }


        setContent {
            JourneysApp()

        }
    }


    private fun findMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        viewModelMap.setLocation(latitude, longitude)
//                        updateMapLocation(latitude, longitude)
                        Log.i(
                            "MainActivity",
                            "setLocation called with latitude: $latitude, longitude: $longitude"
                        )
                    } else {
                        Log.e("MainActivity", "Location is null")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("MainActivity", "Failed to get location", exception)
                }
        } else {
            Log.e("MainActivity", "Location permission not granted")
        }
    }


    @Composable
    fun JourneysApp() {
        val navController = rememberNavController()
        val viewModelMap: ViewModelMap = viewModel()
        val location by viewModelMap.locationLiveData.observeAsState(Pair(0.0, 0.0))

        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { padding ->
            NavigationGraph(navController = navController, location = location)
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
    fun NavigationGraph(navController: NavHostController, location: Pair<Double, Double>) {
        NavHost(navController, startDestination = BottomNavItem.Journeys.route) {
            composable(BottomNavItem.Journeys.route) { JourneysView() }
            composable(BottomNavItem.Map.route) {
                val context = LocalContext.current
                MapView(context = context, latitude = location.first, longitude = location.second)
            }
            composable(BottomNavItem.Settings.route) { SettingsView() }
        }
    }


//@Composable
//fun HomeScreen() {
//    Text(text = "Home Screen")
//}
//
//@Composable
//fun SearchScreen() {
//    Text(text = "Search Screen")
//}
//
//@Composable
//fun ProfileScreen() {
//    Text(text = "Profile Screen")
//}

    sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {
        object Journeys : BottomNavItem("Journeys", Icons.Default.Menu, "journeys_screen")
        object Map : BottomNavItem("Map", Icons.Default.Place, "map_screen")
        object Settings : BottomNavItem("Settings", Icons.Default.Settings, "settings_screen")
    }
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


//@Composable
//fun Journeys(
//    viewModelUtilisateur: ViewModelUser,
//    viewModelJourney: ViewModelJourney,
//    viewModelMap: ViewModelMap,
//    viewModelSettings: ViewModelSettings,
//    navController: NavHostController,
//) {
//    Log.i("System.out", "Lance de Composable MainActivity")
//
//    // Avec l'aide de ChatGPT pour le fonction LaunchedEffect
//    LaunchedEffect(Unit) {
//        if (viewModelUser.user.estVerifie) {
//            navController.navigate("accueil")
//        }
//    }
//
//    NavHost(
//        navController = navController,
//        startDestination = "journeys"
//    ) {
//            composable("se_connecter") { SeConnecter(viewModel, navController) }
//        composable("journeys_screen") { journeysView(viewModelJourney, navController) }
//        composable("map_screen") { mapView(viewModelMap, navController) }
//        composable("settings_screen") { settingsView(viewModelSettings, navController) }
//            composable("ecran_details/{taskId}") { backStackEntry ->
//                val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
//                taskId?.let { EcranDetails(viewModel, navController, it) }
//    }
//}


//    }
//        composable("journeys") { Journey (viewModelJourney, navController) }
//        composable("accueil") {
//            Accueil(
//                viewModelUser,
//                onNavigateToJourney = {
//                    navController.navigate("se_connecter") {
//                        popUpTo("se_connecter")
//                    }
//                },
//                onNavigateToTransactions = {
//                    navController.navigate("ecran_transactions") {
//                        popUpTo("ecran_tranactions") {
//                            inclusive = false
//                            saveState = false
//                        }
//                        restoreState = false
//                    }
//                }
//            )
//        }
//
//        composable("ecran_transactions") {
//            EcranTransactions(
////                viewModelUtilisateur,
//                viewModelJourney,
//                onNavigateToDetails = { idTransaction ->
//                    navController.navigate("ecran_details/$idTransaction") {
//                        popUpTo("accueil") {
//                            inclusive = false
//                            saveState = false
//                        }
//                        restoreState = false
//                    }
//                }
//            )
//        }
//
//        composable(
//            "ecran_details/{idTransaction}",
//            arguments = listOf(navArgument("idTransaction") {
//                type = NavType.StringType
//            })
//        ) { backStackEntry ->
//            val idTransaction = backStackEntry.arguments?.getString("idTransaction")
//            idTransaction?.let {
//                EcranDetails(
////                    viewModelUtilisateur,
//                    viewModelJourney,
//                    idTransaction,
//                    onBackToTransactions = {
//                        navController.navigate("ecran_transactions") {
//                            popUpTo("ecran_transactions") {
//                                inclusive = true
//                                saveState = true
//                            }
//                            restoreState = true
//                        }
//                    },
//                )
//            }
//        }
//    }
//}