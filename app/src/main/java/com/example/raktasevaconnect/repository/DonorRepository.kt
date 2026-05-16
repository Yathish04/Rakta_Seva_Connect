package com.example.raktasevaconnect.repository

import com.example.raktasevaconnect.data.Donor

interface DonorRepository {
    fun isDonorEligible(lastDonationTimestamp: Long): Boolean
    suspend fun registerDonor(donor: Donor)
    suspend fun findMatchingDonors(requiredBloodGroup: String): List<Donor>
}