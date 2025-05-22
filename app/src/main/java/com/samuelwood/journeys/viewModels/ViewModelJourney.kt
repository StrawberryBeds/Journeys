package com.samuelwood.journeys.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot // Still need this for the result
import com.google.firebase.firestore.FirebaseFirestoreException

class ViewModelJourney : ViewModel() {

    fun getAllJourneys() {
        val db = FirebaseFirestore.getInstance()
//        val journeys = hashSetOf(
//            title to title,
//            createdAt to createdAt
//        )
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
