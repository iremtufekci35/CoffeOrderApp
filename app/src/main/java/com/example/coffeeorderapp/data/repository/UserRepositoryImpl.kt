package com.example.coffeeorderapp.data.repository

import com.example.coffeeorderapp.data.local.DataStoreManager
import com.example.coffeeorderapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : UserRepository {
    override suspend fun saveUserEmail(email: String) {
        dataStoreManager.saveEmail(email)
    }

    override fun getUserEmail(): Flow<String?> {
        return dataStoreManager.getEmail
    }
}
