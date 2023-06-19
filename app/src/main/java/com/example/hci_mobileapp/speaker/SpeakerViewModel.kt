package com.example.hci_mobileapp.speaker

import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.R
import com.example.hci_mobileapp.data.network.RetrofitClient
import com.example.hci_mobileapp.data.network.model.Song
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

    fun nameSet(nameToChange: String?){
        if(nameToChange != null)
            _speakerUiState.update { currentState ->
                currentState.copy(name = nameToChange)

            }
    }


    fun setID(ID: String?){
        if(ID != null)
            _speakerUiState.update {currentState ->
                currentState.copy(id = ID)
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
                RetrofitClient.getApiService().doActionBool(
                    actionName = action.toString(),
                    deviceID = uiState.value.id
                )
            }.onFailure {
                /*Thorw Notification to user*/
            }
        }
    }

    fun setVolume(vol: Int){
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionInt(
                    actionName = action.toString(),
                    deviceID = uiState.value.id,
                    params = vol
                )
            }
        }
    }

    fun getPlaylist() {
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().speakerPLaylistGet(
                    actionName = action.toString(),
                    deviceID = uiState.value.id
                )
            }.onSuccess {response ->
                _speakerUiState.update { currentState ->
                    currentState.copy(playList = response.body()?.result)
                }
            }
        }
    }

        fun nextSong() {
            action = "nextSong"
            postJob = viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService().doActionBool(
                        actionName = action.toString(),
                        deviceID = uiState.value.id
                    )
                }.onFailure {
                    /*Thorw Notification to user*/
                }
            }
        }

        fun prevSong() {
            action = "previousSong"
            postJob = viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService().doActionBool(
                        actionName = action.toString(),
                        deviceID = uiState.value.id
                    )
                }.onFailure {
                    /*Thorw Notification to user*/
                }
            }
        }

        fun stop() {
            _speakerUiState.update { currentState ->
                currentState.copy(state = "Stopped")
            }
            action = "stop"
            postJob = viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService().doActionBool(
                        actionName = action.toString(),
                        deviceID = uiState.value.id
                    )
                }.onFailure {
                    /*Thorw Notification to user*/
                }
            }
        }


        fun genreSet(g: String) {
            action = "setGenre"
            postJob = viewModelScope.launch {
                kotlin.runCatching {
                    RetrofitClient.getApiService().doActionString(
                        actionName = action.toString(),
                        deviceID = uiState.value.id,
                        params = g.toLowerCase()
                    )
                }
            }
            _speakerUiState.update { currentState ->
                currentState.copy(currGen = g)

            }
        }
}