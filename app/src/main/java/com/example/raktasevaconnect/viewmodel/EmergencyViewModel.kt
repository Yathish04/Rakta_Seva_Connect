package com.example.raktasevaconnect.viewmodel

import androidx.lifecycle.ViewModel
import com.example.raktasevaconnect.data.Donor
import com.example.raktasevaconnect.utils.LocationUtils
import com.example.raktasevaconnect.utils.RepositoryProvider

class EmergencyViewModel : ViewModel() {

    private val repository = RepositoryProvider.getDonorRepository()

    suspend fun triggerEmergencyAlert(
        hospitalName: String, requiredBloodGroup: String, hospitalLat: Double, hospitalLon: Double
    ): List<Donor> {

        println("🚨 EMERGENCY POSTED: $hospitalName urgently needs $requiredBloodGroup")

        // 1. Fetch potential matches (ViewModel doesn't know if this is Mock or Firebase!)
        val potentialMatches = repository.findMatchingDonors(requiredBloodGroup)

        // 2. Apply Proximity Filter locally
        val notifiedDonors = potentialMatches.filter { donor ->
            LocationUtils.isWithin10KmRadius(
                donorLat = donor.latitude,
                donorLon = donor.longitude,
                hospitalLat = hospitalLat,
                hospitalLon = hospitalLon
            )
        }

        return notifiedDonors
    }
}