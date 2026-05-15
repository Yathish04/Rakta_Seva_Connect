package com.example.raktasevaconnect.data

enum class BloodGroup(val displayName: String) {
    A_POS("A+"),
    A_NEG("A-"),
    B_POS("B+"),
    B_NEG("B-"),
    AB_POS("AB+"),
    AB_NEG("AB-"),
    O_POS("O+"),
    O_NEG("O-");

    companion object {
        // Validation function to check if a typed string matches a real blood group
        fun isValid(input: String): Boolean {
            return entries.any { it.displayName.equals(input, ignoreCase = true) }
        }
    }
}