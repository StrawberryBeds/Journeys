package com.samuelwood.journeys.viewModels

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import android.content.Context
import android.app.Application
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.*


class ViewModelMap(private val context: Context) : ViewModel() {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    fun addNewPosition(
        onSuccess: (latitude: Double, longitude: Double) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        Log.d("ViewModelMap", "addNewPosition called")

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        Log.d("ViewModelMap", "Location obtained: ${location.latitude}, ${location.longitude}")
                        onSuccess(location.latitude, location.longitude)
                    } else {
                        Log.e("ViewModelMap", "Location is null, requesting location updates")
                        requestLocationUpdates(onSuccess, onFailure)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("ViewModelMap", "Failed to get location", exception)
                    onFailure(exception)
                }
        } else {
            Log.e("ViewModelMap", "Location permission not granted")
            onFailure(Exception("Location permission not granted"))
        }
    }

    private fun requestLocationUpdates(
        onSuccess: (latitude: Double, longitude: Double) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    Log.d("ViewModelMap", "Location update obtained: ${location.latitude}, ${location.longitude}")
                    onSuccess(location.latitude, location.longitude)
                } ?: run {
                    Log.e("ViewModelMap", "Location update is null")
                    onFailure(Exception("Location update is null"))
                }
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        } catch (exception: SecurityException) {
            Log.e("ViewModelMap", "Failed to request location updates", exception)
            onFailure(exception)
        }
    }


    fun addJourney(title: String, description: String) {
        val db = FirebaseFirestore.getInstance()
        val journey = hashMapOf(
            "title" to title,
            "description" to description,
            "createdAt" to FieldValue.serverTimestamp()
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


    interface NominatimApiService {
        @GET("search")
        fun searchLocations(
            @Query("q") query: String,
            @Query("format") format: String,
            @Query("limit") limit: Int
        ): Call<List<NominatimResponse>>
    }

    data class NominatimResponse(
        val display_name: String,
        val lat: Double,
        val lon: Double
    )

    object ApiClient {
        private const val BASE_URL = "https://nominatim.openstreetmap.org/"
        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }
}


//val apiService = ApiClient.getClient().create(NominatimApiService::class.java)
//val call = apiService.searchLocations("New York", "json", 10)
//
//call.enqueue(object : Callback<List<NominatimResponse>> {
//    override fun onResponse(
//        call: Call<List<NominatimResponse>>,
//        response: Response<List<NominatimResponse>>
//    ) {
//        if (response.isSuccessful) {
//            val locations = response.body()
//            locations?.forEach { location ->
//                println("Location: ${location.display_name}, Lat: ${location.lat}, Lon: ${location.lon}")
//            }
//        }
//    }
//
//    override fun onFailure(call: Call<List<NominatimResponse>>, t: Throwable) {
//        t.printStackTrace()
//    }
//})
