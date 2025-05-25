package com.samuelwood.journeys.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot // Still need this for the result
import com.google.firebase.firestore.FirebaseFirestoreException
import com.samuelwood.journeys.models.Journey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelJourney : ViewModel() {

    private val _journeys = MutableStateFlow<List<Journey>>(emptyList())
    val journeys: StateFlow<List<Journey>> = _journeys.asStateFlow()

    fun getAllJourneys(title: String) {
        val db = FirebaseFirestore.getInstance()
        val journey = hashMapOf(
            "newTitle" to title,
            "createdAt" to FieldValue.serverTimestamp()
        )
        db.collection("journeys")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    Log.d("Firestore", "Successfully fetched all journeys!")
                    for (document in querySnapshot.documents) {
                        val journeyData = document.data
                        val documentId = document.id // Get the document ID
                        Log.d("Firestore", "${documentId} => ${journeyData}")
                        // You can convert document.data into your Journey data class here
                    }
                } else {
                    Log.d("Firestore", "The journeys collection is empty.")

                }
            }
            .addOnFailureListener { exception ->
                println("Error getting all documents: $exception")
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
