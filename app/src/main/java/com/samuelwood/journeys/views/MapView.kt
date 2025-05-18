package com.samuelwood.journeys.views

import android.content.Context
import android.content.Context.MODE_PRIVATE
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samuelwood.journeys.viewModels.ViewModelMap
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker



@Composable
fun MapView(context: Context, latitude: Double, longitude: Double) {
    val mapView = remember {
        MapView(context).apply {
            Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
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

                }) {
                    Text("Find My Location")
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Button(onClick = {
//                    viewModelMap.startJourney()
                }) {
                    Text("Start Journey")
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





