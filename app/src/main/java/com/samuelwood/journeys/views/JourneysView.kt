package com.samuelwood.journeys.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samuelwood.journeys.viewModels.ViewModelJourney

@Composable
fun JourneysView() {
////    viewModelJourney: ViewModelJourney, navController: NavHostController


    val viewModelJourney: ViewModelJourney = viewModel()
    var newDeparture by remember { mutableStateOf("") }
    var newDestination by remember { mutableStateOf("") }

//    val journeyList by viewModel.resultLiveData.observeAsState(initial = "")


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
////        Row(
////            modifier = Modifier.fillMaxWidth()
////        ) {
////            Button(
////                onClick = {
////                    viewModel.deconecterUtilisateur()
////                    navController.navigate("se_connecter")
////                },
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .padding(start = 8.dp),
////                colors = ButtonColors(
////                    containerColor = Red,
////                    contentColor = White,
////                    disabledContainerColor = Red,
////                    disabledContentColor = White
////                )
////            ) {
////                Text("Se déconnecter ")
////            }
////        }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                "New Journey"
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    value = newDeparture,
                    onValueChange = { newDeparture = it },
                    label = { "New Departure" }, // This is not showing
                    modifier = Modifier
                        .weight(1f)
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
                TextField(
                    value = newDestination,
                    onValueChange = { newDestination = it },
                    label = { "New Destination" }, // This is not showing
                    modifier = Modifier
                        .weight(1f)
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
                    onClick = { viewModelJourney.addJourney(newDeparture, newDestination) },
                    enabled = newDeparture.isNotEmpty() && newDestination.isNotEmpty()
                ) {
                    Text("Add Journey")
                }
            }
        }
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


