package com.example.raktasevaconnect.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.raktasevaconnect.R
import com.example.raktasevaconnect.repository.FirebaseDonorRepository
import com.example.raktasevaconnect.viewmodel.DonorViewModel
import java.util.Calendar

class RegistrationActivity : AppCompatActivity() {

    // Instantiating manually for the academic demo.
    // In a production app, use a ViewModelProvider or Dependency Injection.
    private val repository = FirebaseDonorRepository()
    private val viewModel = DonorViewModel(repository)

    private var selectedDonationDateInMillis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val etName = findViewById<EditText>(R.id.etName)
        val etPhoneNumber = findViewById<EditText>(R.id.etPhoneNumber) // Added Phone Number Field
        val etBloodGroup = findViewById<EditText>(R.id.etBloodGroup)
        val btnSelectDate = findViewById<Button>(R.id.btnSelectDate)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Date Picker Logic
        btnSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    selectedDonationDateInMillis = calendar.timeInMillis
                    btnSelectDate.text = "Date Selected: $dayOfMonth/${month + 1}/$year"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Registration Logic
        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val phone = etPhoneNumber.text.toString() // Capture the phone number
            val bloodGroup = etBloodGroup.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && bloodGroup.isNotEmpty()) {

                // Use the updated ViewModel function that validates the blood group and returns a Boolean
                val isSuccess = viewModel.registerNewDonor(
                    name = name,
                    phoneNumber = phone,
                    bloodGroupInput = bloodGroup,
                    lat = 12.9716, // Example: Simulated local Latitude
                    lon = 77.5946, // Example: Simulated local Longitude
                    lastDonationDate = selectedDonationDateInMillis
                )

                if (isSuccess) {
                    Toast.makeText(this, "Donor Registration successful!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.putExtra("USER_ROLE", "DONOR")
                    intent.putExtra("DONOR_NAME", name)
                    startActivity(intent)
                    finish()
                // Close the registration screen so they can't go "back" to it
                } else {
                    // Show an error if the user typed an invalid blood group string
                    Toast.makeText(this, "Invalid Blood Group. Please use standard formats (e.g., O+, A-).", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}