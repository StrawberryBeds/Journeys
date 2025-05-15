package com.samuelwood.journeys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.samuelwood.journeys.ui.theme.JourneysTheme
//import com.samuelwood.journeys.views.journeysView
import com.samuelwood.journeys.views.mapView



//import androidx.compose.material3.MaterialTheme
//
//
//import androidx.compose.ui.graphics.Color.Companion.Gray
//
//import com.samuelwood.journeys.ui.theme.JourneysTheme
//import com.samuelwood.journeys.viewModels.ViewModelJourney
//import com.samuelwood.journeys.viewModels.ViewModelMap
//import com.samuelwood.journeys.viewModels.ViewModelSettings

class MainActivity : ComponentActivity() {

//    val navController = rememberNavController()

//    private val viewModelUser: ViewModelUser by viewModels()
//    private val viewModelJourney: ViewModelJourney by viewModels()
//    private val viewModelMap: ViewModelMap by viewModels()
//    private val viewModelSettings: ViewModelSettings by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JourneysTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    mapView(
//                      viewModelUser,
//                        viewModelJourney,
//                        viewModelMap,
//                        viewModelSettings,
//                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}







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