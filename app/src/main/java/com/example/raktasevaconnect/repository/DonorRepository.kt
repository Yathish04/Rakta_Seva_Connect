package com.example.raktasevaconnect.repository

import com.example.raktasevaconnect.data.Donor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DonorRepository {

    // 1. Initialize Firestore Database
    private val db = FirebaseFirestore.getInstance()

    fun isDonorEligible(lastDonationTimestamp: Long): Boolean {
        if (lastDonationTimestamp == 0L) return true
        val ninetyDaysInMillis = 90L * 24 * 60 * 60 * 1000
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastDonationTimestamp) >= ninetyDaysInMillis
    }

    // 2. Real Database Registration
    suspend fun registerDonor(donor: Donor) {
        try {
            // Creates a "donors" collection and saves the object using their unique ID
            db.collection("donors").document(donor.donorId).set(donor).await()
            println("✅ Saved successfully to Firebase Cloud Firestore!")
        } catch (e: Exception) {
            println("❌ Error saving donor: ${e.message}")
        }
    }
}