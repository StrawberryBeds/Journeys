package com.samuelwood.journeys.viewModels

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.firestore.FirebaseFirestore

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

    fun addJourney(title: String, description: String) {
        val db = FirebaseFirestore.getInstance()
        val journey = hashMapOf(
            "newTitle" to title,
            "newDescription" to description
        )
        db.collection("journeys")
            .add(journey)
            .addOnSuccessListener { docRef ->
                Log.d("Firestore", "Ajout réussi avec ID : ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Erreur lors de l'ajout", e)
            }
    }
}
