package com.example.raktasevaconnect.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.raktasevaconnect.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnRoleDonor = findViewById<Button>(R.id.btnRoleDonor)
        val btnRoleHospital = findViewById<Button>(R.id.btnRoleHospital)

        // Route to Donor Registration
        btnRoleDonor.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        // Route to Hospital Registration
        btnRoleHospital.setOnClickListener {
            startActivity(Intent(this, HospitalRegistrationActivity::class.java))
        }
    }
}