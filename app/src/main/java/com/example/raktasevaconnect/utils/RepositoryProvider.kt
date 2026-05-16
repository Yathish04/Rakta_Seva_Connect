package com.example.raktasevaconnect.utils

import com.example.raktasevaconnect.repository.DonorRepository
import com.example.raktasevaconnect.repository.FirebaseDonorRepository
import com.example.raktasevaconnect.repository.MockDonorRepository

object RepositoryProvider {
    fun getDonorRepository(): DonorRepository {
        return if (AppConfig.USE_FIREBASE) {
            FirebaseDonorRepository()
        } else {
            MockDonorRepository()
        }
    }
}