package com.samuelwood.journeys.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.samuelwood.journeys.viewModels.ViewModelMap
import com.samuelwood.journeys.viewModels.ViewModelSettings

@Composable
fun settingsView(viewModelSettings: ViewModelSettings, navController: NavHostController) {


//    var newDeparture by remember { mutableStateOf("") }
//    var newDestination by remember { mutableStateOf("") }
////    var estTerminee by remember { mutableStateOf(false) }


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
                "Settings go here",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
