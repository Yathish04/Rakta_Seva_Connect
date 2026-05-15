package com.example.raktasevaconnect.utils

import android.location.Location

object LocationUtils {

    // Calculates distance in kilometers between two GPS coordinates
    fun isWithin10KmRadius(
        donorLat: Double, donorLon: Double,
        hospitalLat: Double, hospitalLon: Double
    ): Boolean {
        val results = FloatArray(1)
        Location.distanceBetween(
            hospitalLat, hospitalLon,
            donorLat, donorLon,
            results
        )
        val distanceInMeters = results[0]
        val distanceInKm = distanceInMeters / 1000.0

        return distanceInKm <= 10.0 // Returns true if 10km or less
    }
}