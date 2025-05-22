package com.samuelwood.journeys.models

import com.google.common.base.Objects
import com.google.firebase.Timestamp

data class Journey(
    val userID: String,
    val title: String,
    val description: String,
    val createdAt: Timestamp,
    val positions: List<Objects>
)