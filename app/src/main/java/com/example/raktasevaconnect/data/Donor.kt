package com.example.raktasevaconnect.data

data class Donor(
    val donorId: String = "", // Document ID in the database
    val name: String = "",
    val phoneNumber: String = "", // Added phone number
    val bloodGroup: String = "",  // Will be validated against the Enum
    val fcmToken: String = "",    // For push notifications later
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val lastDonationDate: Long = 0L,
    val isAvailable: Boolean = true
)