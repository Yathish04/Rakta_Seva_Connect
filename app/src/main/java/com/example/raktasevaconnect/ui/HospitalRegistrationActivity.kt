package com.example.raktasevaconnect.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.raktasevaconnect.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class HospitalRegistrationActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var hospitalLat: Double = 0.0
    private var hospitalLon: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_registration)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val etHospitalName = findViewById<EditText>(R.id.etHospitalName)
        val etHospitalPhone = findViewById<EditText>(R.id.etHospitalPhone)
        val btnGetLocation = findViewById<Button>(R.id.btnGetLocation)
        val tvLocationDisplay = findViewById<TextView>(R.id.tvLocationDisplay)
        val btnRegisterHospital = findViewById<Button>(R.id.btnRegisterHospital)

        // Location Button Logic
        btnGetLocation.setOnClickListener {
            // Check if user granted permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request permission if not granted
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
                return@setOnClickListener
            }

            // Get the last known location
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    hospitalLat = location.latitude
                    hospitalLon = location.longitude
                    tvLocationDisplay.text = "Location: Lat $hospitalLat, Lon $hospitalLon"
                    Toast.makeText(this, "Location captured!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to get location. Turn on GPS.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Registration Logic
        btnRegisterHospital.setOnClickListener {
            val name = etHospitalName.text.toString()
            val phone = etHospitalPhone.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && hospitalLat != 0.0) {
                Toast.makeText(this, "Hospital registered successfully!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("USER_ROLE", "HOSPITAL")
                intent.putExtra("HOSPITAL_NAME", name)
                intent.putExtra("HOSPITAL_PHONE", phone)
                // We will pass the real location to the Dashboard
                intent.putExtra("HOSPITAL_LAT", hospitalLat)
                intent.putExtra("HOSPITAL_LON", hospitalLon)

                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Please fill all fields and get location", Toast.LENGTH_SHORT).show()
            }
        }
    }
}