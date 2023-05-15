package com.example.a2zapplication.data.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.a2zapplication.data.model.firebase.User

@Dao
interface UserDao {

    @Insert
    suspend fun storeUserDetails(user: User)

}