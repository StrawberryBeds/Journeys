package com.samuelwood.journeys.views

import android.R.attr.enabled
import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.style.TextAlign
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


@Composable
fun MapView(context: Context, latitude: Double, longitude: Double) {

    val viewModelMap: ViewModelMap = viewModel()

    var userID by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("Title") }
    var description by remember { mutableStateOf("Description") }
    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val mapView = remember {
        MapView(context).apply {
            Configuration.getInstance()
                .load(context, context.getSharedPreferences("osmdroid", MODE_PRIVATE))
//            setBuiltInZoomControls(true)
            setMultiTouchControls(true)
        }
    }

    // Use Box to layer the MapView and the Button
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)

                    val mapController = controller
                    mapController.setZoom(15.0)
                    val startPoint = GeoPoint(latitude, longitude)
                    mapController.setCenter(startPoint)

                    val marker = Marker(this)
                    marker.position = startPoint
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    marker.title = "Tour Eiffel"
                    overlays.add(marker)
                }
            },
            update = { /* No direct updates for now */ },
            modifier = Modifier.fillMaxSize() // MapView fills the entire Box
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 144.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row {
                Button(onClick = {
                    viewModelMap.findMyLocationButton() // This doesn't work.
                }) {
                    Text("Find My Location")
                }
                Spacer(modifier = Modifier.padding(8.dp))

                Button(onClick = { showDialog = true }) {
                    Text("New Journey")
                }
                if (showDialog) {
                    JourneyDialog(
                        onDismissRequest = { showDialog = false },
                        onConfirmation = {
                            // Handle confirmation action
                            showDialog = false
                        },
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

    var userID by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
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
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
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
                        value = newDescription,
                        onValueChange = { newDescription = it },
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
                            viewModelMap.saveJourney(newTitle, newDescription)
                            showDialog = false
                        },
                        enabled = newTitle.isNotEmpty() && newDescription.isNotEmpty(),
                    ) {
                        Text("Add Journey")
                    }
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





