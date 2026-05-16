package com.example.raktasevaconnect.repository

import com.example.raktasevaconnect.data.Donor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseDonorRepository : DonorRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun isDonorEligible(lastDonationTimestamp: Long): Boolean {
        if (lastDonationTimestamp == 0L) return true
        val ninetyDaysInMillis = 90L * 24 * 60 * 60 * 1000
        return (System.currentTimeMillis() - lastDonationTimestamp) >= ninetyDaysInMillis
    }

    override suspend fun registerDonor(donor: Donor) {
        try {
            db.collection("donors").document(donor.donorId).set(donor).await()
            println("✅ Saved to FIREBASE")
        } catch (e: Exception) {
            println("❌ Firebase Error: ${e.message}")
        }
    }

    override suspend fun findMatchingDonors(requiredBloodGroup: String): List<Donor> {
        val donors = mutableListOf<Donor>()
        try {
            val snapshot = db.collection("donors")
                .whereEqualTo("bloodGroup", requiredBloodGroup)
                .whereEqualTo("available", true)
                .get()
                .await()

            for (document in snapshot.documents) {
                document.toObject(Donor::class.java)?.let { donors.add(it) }
            }
        } catch (e: Exception) {
            println("❌ Firebase Fetch Error: ${e.message}")
        }
        return donors
    }
}