package com.example.a2zapplication.repository.addUserDetails

import com.example.a2zapplication.data.model.firebase.User
import com.example.a2zapplication.data.roomDB.dao.UserDao
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsRepoImp @Inject constructor(private val db : FirebaseFirestore, private val userDao: UserDao): UserDetailsRepo {
    override suspend fun fetchClassDetails() = db.collection("availableClasses").get()
    override suspend fun storeUserDetailsInRoomDB(user : User) {
        userDao.storeUserDetails(user)
    }

}