package com.samuelwood.journeys.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ViewModelJourney : ViewModel() {

    fun addJourney(departure: String, destination: String) {
        val db = FirebaseFirestore.getInstance()
        val journey = hashMapOf(
            "newDeparture" to departure,
            "newDestination" to destination
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

//class JourneysViewModel : ViewModel() {
//    private val _journeyListLiveData = MutableLiveData<String>()
//    val journeyListLiveData: LiveData<String> get() = _journeyListLiveData
//
//    fun myFunction(param: String) {
//        // Function logic here
//        _journeyListLiveData.value = "Processed: $param"
//    }
//}
