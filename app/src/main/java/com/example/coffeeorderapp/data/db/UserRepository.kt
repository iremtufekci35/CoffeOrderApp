package com.example.coffeeorderapp.data.db

import com.example.coffeeorderapp.data.db.dao.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun addUser(user: User) {
        userDao.insert(user)
    }
}
