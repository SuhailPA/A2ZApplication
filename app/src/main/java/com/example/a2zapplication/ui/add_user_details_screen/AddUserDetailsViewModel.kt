package com.example.a2zapplication.ui.add_user_details_screen

import androidx.lifecycle.ViewModel
import com.example.a2zapplication.repository.addUserDetails.UserDetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.concurrent.timerTask

@HiltViewModel
class AddUserDetailsViewModel @Inject constructor(
    private val userDetailsRepo: UserDetailsRepo
) : ViewModel() {



     fun fetchClassDetails() : Flow< List<String>> = flow{
        val querySnapshot =  userDetailsRepo.fetchClassDetails().await()
        val classes = querySnapshot.documents.map {
            documentSnapshot -> documentSnapshot.id
        }
         emit(classes)
    }
}