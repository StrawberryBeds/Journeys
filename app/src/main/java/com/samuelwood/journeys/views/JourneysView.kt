package com.samuelwood.journeys.views

import com.samuelwood.journeys.models.Journey
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samuelwood.journeys.viewModels.ViewModelJourney
import androidx.compose.material3.Button

@Composable
fun JourneysView() {
    val viewModelJourney: ViewModelJourney = viewModel()
    // Collect the state - 'journeys' will now be a List<Journey> where each Journey has an 'id'
    val journeys by viewModelJourney.journeys.collectAsStateWithLifecycle(emptyList<Journey>())


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
            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    // Call the fetch function from the ViewModel
                    viewModelJourney.fetchAllJourneysOnce()
                    // If you implemented the real-time listener, you would call:
                    // viewModelJourney.startListeningForJourneys()
                },
                modifier = Modifier.fillMaxWidth() // Make button fill width
            ) {
                Text("Get All Journeys")
            }

            // Spacer between button and list
            Spacer(modifier = Modifier.padding(bottom = 16.dp))


            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                // Use the 'id' property of the Journey object for the key
                items(journeys, key = { it.id }
                ) { journey -> // 'journey' here is an item from the list, which has the 'id' set
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                        // You can now access journey.id here if needed, e.g., for navigation
                        // .clickable { onNavigateToDetails(journey.id) }
                    ) {
                        Text(
                            text = journey.title, // Access the title property
                            modifier = Modifier.weight(1f).padding(end = 8.dp),
                        )
                        Text(
                            text = journey.description, // Access the description property
                            modifier = Modifier.weight(1f),
                        )
                        // ... (IconButton, etc.)
                    }
                }
            }
        }
    }
}

//                            IconButton(onClick = {
//                                val journeyID = journey.journeyID
//                                onNavigateToDetails(journeyID) }) {
//                                Icon(Icons.Default.Edit, contentDescription = "Modifier")
//                            }




