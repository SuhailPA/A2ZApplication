package com.example.a2zapplication.ui.add_user_details_screen

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.a2zapplication.data.model.firebase.User
import com.example.a2zapplication.repository.addUserDetails.UserDetailsRepo
import com.example.a2zapplication.repository.requestAccess.BaseDbAccess
import com.example.a2zapplication.utils.AllEvents
import com.example.a2zapplication.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.concurrent.timerTask

@HiltViewModel
class AddUserDetailsViewModel @Inject constructor(
    private val repository : BaseDbAccess
) : ViewModel() {

    val allChannel = Channel<AllEvents>()
    val allEvents = allChannel.receiveAsFlow().asLiveData()
     fun fetchClassDetails() : Flow< List<String>> = flow{
        val querySnapshot =  repository.fetchClassDetails().await()
        val classes = querySnapshot.documents.map {
            documentSnapshot -> documentSnapshot.id
        }
         emit(classes)
    }

    fun sendRequestForAccess(user :User) {
        viewModelScope.launch {
            repository.setRequestForAccess(user)?.addOnCompleteListener {
                viewModelScope.launch {
                    if (it.isSuccessful){
                        repository.storeUserDetailsInRoom(user)
                        allChannel.send(AllEvents.Message(Messages.REQUEST_SENT))
                    }
                }
            }
        }
    }
}