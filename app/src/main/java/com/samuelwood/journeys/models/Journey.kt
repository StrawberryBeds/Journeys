package com.samuelwood.journeys.models

import com.google.firebase.Timestamp

// Corrected Journey data class with an 'id' field
data class Journey(
    val id: String = "", // Added id field
    val userID: String = "", // Give default values for toObject compatibility
    val title: String = "", // Give default values
    val description: String = "", // Give default values
    val createdAt: Timestamp? = null, // Timestamp can be null initially or if missing
    val positions: List<Any> = emptyList() // Using List<Any> as a temporary placeholder
) {
    // Add a no-argument constructor for Firestore's toObject()
    constructor() : this("", "", "", "", null, emptyList())
}
