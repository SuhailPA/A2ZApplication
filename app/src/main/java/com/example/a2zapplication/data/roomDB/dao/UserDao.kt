package com.example.a2zapplication.data.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.a2zapplication.data.roomDB.UserDetails

@Dao
interface UserDao {

    @Insert
    fun storeUserDetails(user: UserDetails)

}