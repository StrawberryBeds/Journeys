package com.samuelwood.journeys.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelMap : ViewModel() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun findMyLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location: Location? ->
//                    location?.let {
//                        val latitude = it.latitude
//                        val longitude = it.longitude
//                        // Use latitude and longitude as needed
//                    }
//                }
//        }
    }

    fun addJourney(title: String, description: String) {
        val db = FirebaseFirestore.getInstance()
        val journey = hashMapOf(
            "newTitle" to title,
            "newDescription" to description,
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

    fun addNewLocation() {

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
