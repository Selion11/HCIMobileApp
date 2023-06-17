package com.example.hci_mobileapp.speaker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.data.network.RetrofitClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpeakerViewModel : ViewModel() {
    private val _speakerUiState = MutableStateFlow(SpeakerUiState())

    val uiState :StateFlow<SpeakerUiState> = _speakerUiState.asStateFlow()


    private var postJob: Job? = null

    private var action: String? = null

    fun nameSet(nameToChange: String){
        _speakerUiState.update { currentState ->
            currentState.copy(name = nameToChange)

        }
    }
    fun iconSelectPlay(): Int{
        return if(uiState.value.state == "Paused"){
            uiState.value.icons.pause
        } else
            uiState.value.icons.play
    }

    fun playPause() {
        _speakerUiState.update { currentState ->
            if (uiState.value.state == "Playing")
                currentState.copy(state = "Paused")
            if(uiState.value.state == "Stopped")
                currentState.copy(state = "Playing")
            else
                currentState.copy(state = "Resuming")
        }

        action = when(uiState.value.state){
            "Paused" -> "pause"
            "Playing" -> "play"
            else -> "resume"
        }
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doAction(
                    actionName = action.toString(),
                    deviceID = uiState.value.id
                )
            }.onFailure {
                /*Thorw Notification to user*/
            }
        }
    }

    fun nextSong(){
        action = "nextSong"
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doAction(
                    actionName = action.toString(),
                    deviceID = uiState.value.id
                )
            }.onFailure {
                /*Thorw Notification to user*/
            }
        }
    }

    fun prevSong(){
        action = "previousSong"
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doAction(
                    actionName = action.toString(),
                    deviceID = uiState.value.id
                )
            }.onFailure {
                /*Thorw Notification to user*/
            }
        }
    }

    fun stop(){
        _speakerUiState.update { currentState ->
            currentState.copy(state = "Stopped")
        }
        action = "stop"
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doAction(
                    actionName = action.toString(),
                    deviceID = uiState.value.id
                )
            }.onFailure {
                /*Thorw Notification to user*/
            }
        }
    }


    fun genreSet(genre: String) {
        _speakerUiState.update { currentState ->
            currentState.copy(currGen = genre)

        }
    }
}