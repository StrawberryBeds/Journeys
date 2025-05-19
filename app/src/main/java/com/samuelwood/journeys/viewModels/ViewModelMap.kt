package com.samuelwood.journeys.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class ViewModelMap : ViewModel() {
    private val _locationLiveData = MutableLiveData<Pair<Double, Double>>()
    val locationLiveData = _locationLiveData

//    val location =
//        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

    fun setLocation(latitude: Double, longitude: Double) {
        _locationLiveData.value = Pair(latitude, longitude)
    }


    fun findMyLocationButton() {
        setLocation(37.4219983, -122.084)


    }

    fun saveJourney(
//        userID: String,
        title: String,
        description: String,
//        createdAt: Timestamp.Companion,
//        positions: Any
    ) {
        val db = FirebaseFirestore.getInstance()
        val journey = hashMapOf(
//            "userID" to userID,
            "title" to  title,
            "decription" to description,
//            "createdAt" to createdAt,
//            "positions" to positions
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


//    fun saveJourney(departure: String, destination: String) {
//        val db = FirebaseFirestore.getInstance()
//        val journey = hashMapOf(
//            "newDeparture" to departure,
//            "newDestination" to destination
//        )
//        db.collection("journeys")
//            .add(journey)
//            .addOnSuccessListener { docRef ->
//                Log.d("Firestore", "Ajout réussi avec ID : ${docRef.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w("Firestore", "Erreur lors de l'ajout", e)
//            }
//    }

