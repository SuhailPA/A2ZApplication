package com.example.a2zapplication.ui.request_access_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.a2zapplication.repository.requestAccess.BaseDbAccess
import com.example.a2zapplication.utils.AllEvents
import com.example.a2zapplication.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestAccessViewModel @Inject constructor(private val repository : BaseDbAccess) : ViewModel() {


}