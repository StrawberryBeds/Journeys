package com.samuelwood.journeys.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.samuelwood.journeys.models.Journey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ViewModelJourney : ViewModel() {

    private val _journeys = MutableStateFlow<List<Journey>>(emptyList())
    val journeys: StateFlow<List<Journey>> = _journeys.asStateFlow()

    fun fetchAllJourneysOnce() {
        val db = FirebaseFirestore.getInstance()

        db.collection("journeys")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    Log.d("Firestore", "Successfully fetched ${querySnapshot.size()} journeys!")
                    val journeyList = mutableListOf<Journey>()
                    for (document in querySnapshot.documents) {
                        // Convert document data to Journey object
                        val journey = document.toObject(Journey::class.java)

                        if (journey != null) {
                            val journeyWithId =
                                journey.copy(id = document.id) // Create a copy with the ID set
                            journeyList.add(journeyWithId)
                            Log.d("Firestore", "${document.id} => ${document.data}")
                        } else {
                            Log.w(
                                "Firestore",
                                "Failed to convert document ${document.id} to Journey object."
                            )
                        }
                    }
                    // Update the StateFlow
                    viewModelScope.launch {
                        _journeys.value = journeyList
                        Log.d("Firestore", "StateFlow updated with ${journeyList.size} journeys.")
                    }

                } else {
                    Log.d("Firestore", "The journeys collection is empty or null.")
                    viewModelScope.launch {
                        _journeys.value = emptyList()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting all documents: ", exception)
                // Handle error state
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
