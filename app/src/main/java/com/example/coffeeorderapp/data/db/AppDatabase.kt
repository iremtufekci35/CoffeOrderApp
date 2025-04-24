package com.example.coffeeorderapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coffeeorderapp.data.db.dao.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
