package com.samuelwood.journeys.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.samuelwood.journeys.viewModels.ViewModelJourney

@Composable
fun JourneysView() {
////    viewModelJourney: ViewModelJourney, navController: NavHostController


    val viewModelJourney: ViewModelJourney = viewModel()
    var title by remember { mutableStateOf("") }
//    var createdAt by remember { mutableStateOf("") }

//    val journeys by viewModelJourney.getAllJourneys().collectAsState()
//    val journey by viewModelJourney.getAllJourney(nomUtilisateur.toString()).collectAsState()

//    val journeyList by viewModelJourney.resultLiveData.observeAsState(initial = "")


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

            Text(
                "My Journeys"
            )

//            Spacer(modifier = Modifier.padding(8.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                TextField(
//                    value = newDeparture,
//                    onValueChange = { newDeparture = it },
//                    label = { "New Departure" }, // This is not showing
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(8.dp)
//                )
//            }
//            Spacer(modifier = Modifier.padding(8.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                TextField(
//                    value = newDestination,
//                    onValueChange = { newDestination = it },
//                    label = { "New Destination" }, // This is not showing
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(8.dp)
//                )
//            }
//            Spacer(modifier = Modifier.padding(8.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Button(
//                    onClick = { viewModelJourney.addJourney(newDeparture, newDestination) },
//                    enabled = newDeparture.isNotEmpty() && newDestination.isNotEmpty()
//                ) {
//                    Text("Add Journey")
//                }
            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        viewModelJourney.getAllJourneys(title)
                    },
                ) {
                    Text("Get All Journeys")
                }

//                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
//                    items(journeys, key = { it.journeyID }) { journey ->
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            // Calculer la couleur en fonction de la condition
//
//                            IconButton(onClick = {
//                                val journeyID = journey.journeyID
//                                onNavigateToDetails(journeyID) }) {
//                                Icon(Icons.Default.Edit, contentDescription = "Modifier")
//                            }
//                            Text(
//                                "${journey.title}",
//                                modifier = Modifier.weight(1f)
//                            )
//                            Text(
//                                journey.description,
//                                modifier = Modifier.weight(1f)
//                            )
//                        }
//                    }
//                }
            }
        }
    }
}




