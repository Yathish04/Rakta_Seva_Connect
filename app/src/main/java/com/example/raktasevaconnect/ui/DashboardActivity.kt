package com.example.raktasevaconnect.ui

import android.app.AlertDialog
import android.media.RingtoneManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.raktasevaconnect.R
import com.example.raktasevaconnect.data.Donor
import com.example.raktasevaconnect.viewmodel.EmergencyViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private val viewModel = EmergencyViewModel()
    private lateinit var tvAcceptedDonorsList: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // UI References
        val tvWelcomeMessage = findViewById<TextView>(R.id.tvWelcomeMessage)
        val layoutHospitalView = findViewById<LinearLayout>(R.id.layoutHospitalView)
        val layoutDonorView = findViewById<LinearLayout>(R.id.layoutDonorView)
        val btnPostEmergency = findViewById<Button>(R.id.btnPostEmergency)
        tvAcceptedDonorsList = findViewById(R.id.tvAcceptedDonorsList)

        // 1. Determine who logged in
        val userRole = intent.getStringExtra("USER_ROLE")

        if (userRole == "HOSPITAL") {
            // HOSPITAL DASHBOARD
            val hospitalName = intent.getStringExtra("HOSPITAL_NAME") ?: "Hospital"
            val hospitalPhone = intent.getStringExtra("HOSPITAL_PHONE") ?: "Unknown"

            tvWelcomeMessage.text = "$hospitalName Dashboard"
            layoutHospitalView.visibility = View.VISIBLE
            layoutDonorView.visibility = View.GONE

            btnPostEmergency.setOnClickListener {
                // Wrap the network call in a coroutine
                lifecycleScope.launch {
                    val matchingDonors = viewModel.triggerEmergencyAlert(
                        hospitalName = hospitalName,
                        requiredBloodGroup = "O+", // You can eventually make this dynamic!
                        hospitalLat = intent.getDoubleExtra("HOSPITAL_LAT", 12.9710),
                        hospitalLon = intent.getDoubleExtra("HOSPITAL_LON", 77.5940)
                    )

                    if (matchingDonors.isNotEmpty()) {
                        playNotificationSound()
                        simulateIncomingDonorNotification(matchingDonors.first(), hospitalPhone)
                    } else {
                        Toast.makeText(this@DashboardActivity, "No eligible donors found within 10km.", Toast.LENGTH_LONG).show()
                    }
                }
            }

        } else if (userRole == "DONOR") {
            // DONOR DASHBOARD
            val donorName = intent.getStringExtra("DONOR_NAME") ?: "Donor"

            tvWelcomeMessage.text = "Welcome, $donorName"
            layoutHospitalView.visibility = View.GONE
            layoutDonorView.visibility = View.VISIBLE
        }
    }

    // Plays the default system notification sound
    private fun playNotificationSound() {
        try {
            val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(applicationContext, notificationUri)
            ringtone.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Phase 4: Simulated FCM Notification & TWO-WAY Privacy Gate
    private fun simulateIncomingDonorNotification(donor: Donor, hospitalPhone: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("🚨 URGENT BLOOD REQUEST")
        builder.setMessage(
            "Hello ${donor.name},\n\n" +
                    "A nearby patient urgently needs ${donor.bloodGroup} blood.\n\n" +
                    "Location: < 10km away\n\n" +
                    "Can you donate right now?"
        )

        // Privacy Rule: Two-Way Reveal on Accept
        builder.setPositiveButton("ACCEPT & REVEAL") { dialog, _ ->

            // 1. Reveal Hospital Number to the Donor
            Toast.makeText(this, "Hospital Contact: $hospitalPhone. Thank you!", Toast.LENGTH_LONG).show()

            // 2. Reveal Donor Number to the Hospital (Updating the Hospital's UI)
            val revealText = "✅ ${donor.name} (${donor.bloodGroup}) is on the way!\nContact: ${donor.phoneNumber}"
            tvAcceptedDonorsList.text = revealText
            tvAcceptedDonorsList.setTextColor(android.graphics.Color.parseColor("#E91E63")) // Highlight it

            println("PRIVACY UNLOCKED: Mutual contact details shared.")
            dialog.dismiss()
        }

        builder.setNegativeButton("DECLINE") { dialog, _ ->
            Toast.makeText(this, "Request declined. Finding next donor...", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}