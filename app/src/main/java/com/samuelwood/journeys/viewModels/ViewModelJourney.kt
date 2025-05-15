package com.samuelwood.journeys.viewModels

import com.samuelwood.journeys.models.Journey

class ViewModelJourney {

    fun addJourney(departure: String, destination: String) {
//        val nouvelleIDTache = (taches.maxOfOrNull { it.idTache } ?: 0) + 1


        val newJourney = Journey(
//            idTache = nouvelleIDTache,
            departure = departure,
            destination = destination
        )
//        _taches.add(nouvelleTache)
//        sauvegarderAuPreferences()
    }
}