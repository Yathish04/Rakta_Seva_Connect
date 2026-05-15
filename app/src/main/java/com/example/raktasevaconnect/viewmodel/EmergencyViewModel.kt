package com.example.raktasevaconnect.viewmodel

import androidx.lifecycle.ViewModel
import com.example.raktasevaconnect.data.Donor
import com.example.raktasevaconnect.data.EmergencyRequest
import com.example.raktasevaconnect.utils.LocationUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class EmergencyViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Notice the 'suspend' keyword here—it waits for Firebase to reply!
    suspend fun triggerEmergencyAlert(
        hospitalName: String,
        requiredBloodGroup: String,
        hospitalLat: Double,
        hospitalLon: Double
    ): List<Donor> {

        val request = EmergencyRequest(
            requestId = UUID.randomUUID().toString(),
            hospitalName = hospitalName,
            requiredBloodGroup = requiredBloodGroup,
            latitude = hospitalLat,
            longitude = hospitalLon,
            timestamp = System.currentTimeMillis()
        )

        println("🚨 EMERGENCY POSTED: ${request.hospitalName} urgently needs ${request.requiredBloodGroup}")

        val notifiedDonors = mutableListOf<Donor>()

        try {
            // 1. Query Firebase: Only get donors who match the blood group AND are available
            val snapshot = db.collection("donors")
                .whereEqualTo("bloodGroup", requiredBloodGroup)
                .whereEqualTo("available", true)
                .get()
                .await()

            // 2. Loop through the cloud results
            for (document in snapshot.documents) {
                val donor = document.toObject(Donor::class.java)
                if (donor != null) {

                    // 3. Apply the Proximity Filter (Within 10km)
                    val isNearby = LocationUtils.isWithin10KmRadius(
                        donorLat = donor.latitude,
                        donorLon = donor.longitude,
                        hospitalLat = hospitalLat,
                        hospitalLon = hospitalLon
                    )

                    if (isNearby) {
                        notifiedDonors.add(donor)
                    }
                }
            }
        } catch (e: Exception) {
            println("❌ Error fetching from Firebase: ${e.message}")
        }

        return notifiedDonors
    }
}