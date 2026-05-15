package com.example.raktasevaconnect.data

data class Hospital(
    val hospitalId: String = "", // Document ID in the database
    val hospitalName: String = "",
    val contactPhone: String = "", // Added phone number
    val authorizationCode: String = "", // A simple security check for the academic project
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)