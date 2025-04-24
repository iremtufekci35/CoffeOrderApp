package com.example.coffeeorderapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUserEmail(email: String)
    fun getUserEmail(): Flow<String?>
}
