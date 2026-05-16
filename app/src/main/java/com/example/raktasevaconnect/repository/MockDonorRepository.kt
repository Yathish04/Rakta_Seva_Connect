package com.example.raktasevaconnect.repository

import com.example.raktasevaconnect.data.Donor

class MockDonorRepository : DonorRepository {

    // A temporary local list to hold registered users while the app is running
    private val localDatabase = mutableListOf<Donor>()

    init {
        // Pre-populate with some fake data so the dashboard always has someone to find
        localDatabase.add(Donor("1", "Ravi (Mock Match)", "9876543210", "O+", "token1", 12.9716, 77.5946, 0L, true))
        localDatabase.add(Donor("2", "Sara (Mock Wrong Blood)", "9876543213", "A+", "token4", 12.9716, 77.5946, 0L, true))
    }

    override fun isDonorEligible(lastDonationTimestamp: Long): Boolean {
        if (lastDonationTimestamp == 0L) return true
        val ninetyDaysInMillis = 90L * 24 * 60 * 60 * 1000
        return (System.currentTimeMillis() - lastDonationTimestamp) >= ninetyDaysInMillis
    }

    override suspend fun registerDonor(donor: Donor) {
        localDatabase.add(donor)
        println("✅ Saved to MOCK DATABASE")
    }

    override suspend fun findMatchingDonors(requiredBloodGroup: String): List<Donor> {
        // Filter the local list instead of querying the cloud
        return localDatabase.filter {
            it.bloodGroup.equals(requiredBloodGroup, ignoreCase = true) && it.isAvailable
        }
    }
}