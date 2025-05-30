package com.samuelwood.journeys.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Timestamp
import com.samuelwood.journeys.viewModels.ViewModelMap
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import android.preference.PreferenceManager
import androidx.compose.material3.AlertDialog

@Composable
fun MapScreen(permissionGranted: Boolean) {
    val context = LocalContext.current
    val viewModelMap = remember { ViewModelMap(context.applicationContext) }
    var showDialog by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }

    val onSuccess: (latitude: Double, longitude: Double) -> Unit = { latitude, longitude ->
        showAlert = true
    }

    val onFailure: (Exception) -> Unit = { exception ->
        Log.e("MapView", "Failed to add new position", exception)
    }

    DisposableEffect(Unit) {
        Configuration.getInstance().apply {
            load(context, PreferenceManager.getDefaultSharedPreferences(context))
        }
        onDispose { }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    val mapController = controller
                    mapController.setZoom(15.0)
                    val startPoint = GeoPoint(45.5520559, -73.6420105)
                    mapController.setCenter(startPoint)

                    val marker = Marker(this)
                    marker.position = startPoint
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    marker.title = "College Ahuntsic"
                    overlays.add(marker)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 144.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Button(
                    onClick = {
                        if (permissionGranted) {
                            viewModelMap.addNewPosition(onSuccess, onFailure)
                        } else {
                            // Optionally, show a message to the user that permission is required
                            Toast.makeText(context, "Location permission is required", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Add a Position")
                }

                if (showAlert) {
                    AlertDialog(
                        onDismissRequest = { showAlert = false },
                        title = { Text("Success") },
                        text = { Text("New position added successfully!") },
                        confirmButton = {
                            Button(onClick = { showAlert = false }) {
                                Text("OK")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Button(onClick = { showDialog = true }) {
                    Text("New Journey")
                }

                if (showDialog) {
                    JourneyDialog(
                        onDismissRequest = { showDialog = false },
                        onConfirmation = { showDialog = false },
                        dialogTitle = "Alert Title",
                        dialogText = "This is the message of the alert dialog."
                    )
                }
            }
        }
    }
}



@Composable
fun JourneyDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    val viewModelMap: ViewModelMap = viewModel()

//    var userID by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
//    var newTitle by remember { mutableStateOf("") }
    var newCreatedAt by remember { mutableStateOf(Timestamp)}
    var description by remember { mutableStateOf("") }

//    var newDescription by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
//    var createdAt by remember { mutableStateOf("")}
//    var positions by remember { mutableListOf() }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Journey Title and Description",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
//                var newTitle = null
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { "Journey Title" }, // This is not showing
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }



            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { "New Description" }, // This is not showing
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        viewModelMap.addJourney(title, description)
                        showDialog = false
                    },
                    enabled = title.isNotEmpty() && description.isNotEmpty(),
                ) {
                    Text("Add Journey")
                }
            }
        }
    }
}

//        { view ->
//            // Observe the lifecycle and call onResume/onPause
//            DisposableEffect(lifecycleOwner) {
//                val observer = LifecycleEventObserver { _, event ->
//                    when (event) {
//                        Lifecycle.Event.ON_RESUME -> view.onResume()
//                        Lifecycle.Event.ON_PAUSE -> view.onPause()
//                        else -> {}
//                    }
//                }
//                lifecycleOwner.lifecycle.addObserver(observer)
//                onDispose {
//                    lifecycleOwner.lifecycle.removeObserver(observer)
//                }
//            }
//        }





