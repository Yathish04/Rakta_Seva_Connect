package com.example.raktasevaconnect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raktasevaconnect.data.BloodGroup
import com.example.raktasevaconnect.data.Donor
import com.example.raktasevaconnect.repository.DonorRepository // Make sure this is imported!
import com.example.raktasevaconnect.utils.RepositoryProvider
import kotlinx.coroutines.launch
import java.util.UUID

// FIXED: Added explicit type ": DonorRepository"
class DonorViewModel(private val repository: DonorRepository = RepositoryProvider.getDonorRepository()) : ViewModel() {

    fun registerNewDonor(
        name: String,
        phoneNumber: String,
        bloodGroupInput: String,
        lat: Double,
        lon: Double,
        lastDonationDate: Long
    ): Boolean {
        // 1. STRICT VALIDATION: Reject fake blood groups (like "I+")
        if (!BloodGroup.isValid(bloodGroupInput)) {
            println("Registration Failed: Invalid Blood Group entered.")
            return false // Tell the UI the registration failed
        }

        // 2. Format the valid blood group properly
        val formattedBloodGroup = bloodGroupInput.trim().uppercase()

        // 3. Check 90-day eligibility
        val eligibleToDonate = repository.isDonorEligible(lastDonationDate)

        // 4. Create the new structured Donor record
        val newDonor = Donor(
            donorId = UUID.randomUUID().toString(),
            name = name,
            phoneNumber = phoneNumber, // Saving the phone number
            bloodGroup = formattedBloodGroup,
            fcmToken = "pending_token",
            latitude = lat,
            longitude = lon,
            lastDonationDate = lastDonationDate,
            isAvailable = eligibleToDonate
        )

        // 5. Save to database
        viewModelScope.launch {
            repository.registerDonor(newDonor)
        }

        return true // Tell the UI the registration was successful
    }
}