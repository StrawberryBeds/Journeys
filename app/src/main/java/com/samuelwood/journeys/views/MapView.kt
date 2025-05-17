package com.samuelwood.journeys.views

import android.preference.PreferenceManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samuelwood.journeys.viewModels.ViewModelJourney
import com.samuelwood.journeys.viewModels.ViewModelMap
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable

fun MapView() {

    val viewModelMap: ViewModelMap = viewModel()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Initialize OSMDroid configuration once
    remember {
        Configuration.getInstance().load(
            context,
            PreferenceManager.getDefaultSharedPreferences(context)
        )
        true
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
                    val startPoint = GeoPoint(48.8583, 2.2944)
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

        // Position the button at the bottom and center-horizontally
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 144.dp), // Equivalent to android:layout_marginBottom
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom // Not strictly needed here as it's the only item
        ) {
            Row {
                Button(onClick = {
                    viewModelMap.findMyLocation()
                }) {
                    Text("Find My Location")
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Button(onClick = {
                    viewModelMap.startJourney()
                }) {
                    Text("Start Journey")
                }
            }
        }
    }
}





