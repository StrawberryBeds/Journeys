package com.samuelwood.journeys.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class ViewModelMap : ViewModel() {
    private val _locationLiveData = MutableLiveData<Pair<Double, Double>>()
    val locationLiveData = _locationLiveData

    fun setLocation(latitude: Double, longitude: Double) {
        _locationLiveData.value = Pair(latitude, longitude)
    }



    fun findMyLocation() {


    }

    fun startJourney () {

    }
}