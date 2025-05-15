package com.samuelwood.journeys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import com.samuelwood.journeys.models.Journey
import com.samuelwood.journeys.ui.theme.JourneysTheme
import com.samuelwood.journeys.viewModels.ViewModelJourney
import com.samuelwood.journeys.viewModels.ViewModelMap
import com.samuelwood.journeys.viewModels.ViewModelSettings

class MainActivity : ComponentActivity() {

//    private val viewModelUser: ViewModelUser by viewModels()
    private val viewModelJourney: ViewModelJourney by viewModels()
    private val viewModelMap: ViewModelMap by viewModels()
    private val viewModelSettings: ViewModelSettings by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JourneysTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    journeysView(
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

@Composable
fun journeysView(
//    viewModelJourney: ViewModelJourney, navController: NavHostController
) {


    var newDeparture by remember { mutableStateOf("") }
    var newDestination by remember { mutableStateOf("") }
//    var estTerminee by remember { mutableStateOf(false) }


    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(8.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Button(
//                onClick = {
//                    viewModel.deconecterUtilisateur()
//                    navController.navigate("se_connecter")
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 8.dp),
//                colors = ButtonColors(
//                    containerColor = Red,
//                    contentColor = White,
//                    disabledContainerColor = Red,
//                    disabledContentColor = White
//                )
//            ) {
//                Text("Se déconnecter ")
//            }
//        }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                "New Journey",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BasicTextField(
                    value = newDeparture,
                    onValueChange = { newDeparture = it },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Gray)
                        .padding(8.dp)
                )
            }
            Spacer(
                modifier = Modifier.padding(8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BasicTextField(
                    value = newDestination,
                    onValueChange = { newDestination = it },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Gray)
                        .padding(8.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (newDestination.isNotBlank()) {
                            addJourney(newDeparture, newDestination)
                            newDeparture = ""
                            newDestination = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Text("Add Journey")
                }
            }
//        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
//            items(items = viewModelJourney.journeys) { journey ->
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    IconButton(onClick = { navController.navigate("map_screen/${journey.idJourney}") }) {
//                        Icon(Icons.Default.Edit, contentDescription = "Modifier")
//                    }
//                    Text(journey.nomJourney, modifier = Modifier.weight(1f))
//
//                    IconButton(onClick = { viewModelJourney.deleteJourney(idTache = journey.idTache) }) {
//                        Icon(Icons.Default.Delete, contentDescription = "Supprimer")
//                    }
//                }
//            }
//        }
        }
    }
}

fun addJourney(departure: String, destination: String) {
//        val newJourneyID = (journeys.maxOfOrNull { it.journeyID } ?: 0) + 1


    val newJourney = Journey(
//            journeyID = newJourneyID,
        departure = departure,
        destination = destination
    )
//        _journeys.add(newJourney)
//        saveToPrefs()
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