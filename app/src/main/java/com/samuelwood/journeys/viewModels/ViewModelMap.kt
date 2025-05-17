package com.samuelwood.journeys.viewModels

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient

class ViewModelMap : ViewModel() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun findMyLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location: Location? ->
//                    location?.let {
//                        val latitude = it.latitude
//                        val longitude = it.longitude
//                        // Use latitude and longitude as needed
//                    }
//                }
//        }
    }

    fun startJourney () {

    }
}