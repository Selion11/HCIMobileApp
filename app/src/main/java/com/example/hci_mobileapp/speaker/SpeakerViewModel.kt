package com.example.hci_mobileapp.speaker

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.MainActivity
import com.example.hci_mobileapp.R
import com.example.hci_mobileapp.data.network.RetrofitClient
import com.example.hci_mobileapp.data.network.model.ApiDevice
import com.example.hci_mobileapp.data.network.model.Song
import com.example.hci_mobileapp.notification.MyApplication
import com.example.hci_mobileapp.notification.ShowNotificationReceiver
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpeakerViewModel(device: ApiDevice) : ViewModel() {
    private val _speakerUiState = MutableStateFlow(SpeakerUiState())
    val alo = device
    init {
        _speakerUiState.value = SpeakerUiState(
            name = device.name,
            id= device.id,
            state = device.state?.status,
            volume = device.state?.volume,
            currGen = device.state?.genre
        )

        _speakerUiState.value.volume?.let { setVolume(it) }
        _speakerUiState.value.currGen?.let { genreSet(it) }
    }

    val uiState :StateFlow<SpeakerUiState> = _speakerUiState.asStateFlow()
/*

    val notifs = ShowNotificationReceiver()

    fun generateNotification(){
        notifs.showNotification(MainActivity(),alo)
    }
*/

    private var postJob: Job? = null

    private var action: String? = null

    fun iconSelectPlay(): Int {
        return if(uiState.value.state == "paused"){
            uiState.value.icons.pause
        } else
            uiState.value.icons.play
    }

    fun playPause() {
        postJob?.cancel()
        _speakerUiState.update { currentState ->
            when (uiState.value.state) {
                "playing" -> currentState.copy(state = "paused")
                "stopped" -> currentState.copy(state = "playing")
                else -> currentState.copy(state = "playing")
            }
        }

        action = when(uiState.value.state){
            "paused" -> "pause"
            "playing" -> "play"
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
        postJob = null
    }

    fun setVolume(vol: Int){
        postJob?.cancel()
        if(vol < 0 || vol > 10){
            //notifs
        }else{
            action = "setVolume"
            postJob = viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService().doActionInt(
                        actionName = action.toString(),
                        deviceID = uiState.value.id,
                        params = listOf(vol)
                    )
                }
            }

            _speakerUiState.update {currentState ->
                currentState.copy(volume = vol)
            }
        }
    }

    fun getPlaylist() {
        postJob?.cancel()
        action = "getPlaylist"
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().speakerPLaylistGet(
                    actionName = action,
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
            postJob?.cancel()
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
            postJob?.cancel()
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
            postJob?.cancel()
            _speakerUiState.update { currentState ->
                currentState.copy(state = "stopped")
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
            postJob?.cancel()
            action = "setGenre"
            postJob = viewModelScope.launch {
                kotlin.runCatching {
                    RetrofitClient.getApiService().doActionString(
                        actionName = action.toString(),
                        deviceID = uiState.value.id,
                        params = listOf(g.lowercase())
                    )
                }
            }
            _speakerUiState.update { currentState ->
                currentState.copy(currGen = g)

            }
        }
}