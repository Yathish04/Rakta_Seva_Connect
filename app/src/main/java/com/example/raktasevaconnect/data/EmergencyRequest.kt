package com.example.raktasevaconnect.data

data class EmergencyRequest(
    val requestId: String,
    val hospitalName: String,
    val requiredBloodGroup: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val status: String = "ACTIVE" // Can be ACTIVE, FULFILLED, or CANCELLED
)