package com.example.a2zapplication.data.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.a2zapplication.data.model.firebase.User
import com.example.a2zapplication.data.roomDB.dao.UserDao

@Database(entities = [User::class], version = 1)
abstract class A2ZDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}