package com.example.hci_mobileapp.speaker


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.data.network.RetrofitClient
import com.example.hci_mobileapp.data.network.model.ApiDevice
import com.example.hci_mobileapp.devicesView.DevicesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class SpeakerViewModel(device: ApiDevice, parent: DevicesViewModel) : ViewModel() {
    private val _speakerUiState = MutableStateFlow(SpeakerUiState())

    private val par = parent

    init {
        _speakerUiState.value = SpeakerUiState(
            name = device.name,
            id= device.id,
            state = device.state?.status,
        )
        device.state?.volume?.let { setVolume(it,false) }
        device.state?.genre?.let { genreSet(it,false) }
    }

    private fun skipNoti(flag : Boolean? = true){
        if (flag == true){
            uiState.value.id?.let { par.notifGenerate(it) }
        }
    }

    val uiState :StateFlow<SpeakerUiState> = _speakerUiState.asStateFlow()

    private var postJob: Job? = null

    private var action: String? = null

    fun iconSelectPlay(): Int {
        return if(uiState.value.state == "paused" || uiState.value.state == "stopped"){
            uiState.value.icons.play
        } else
            uiState.value.icons.pause
    }

    fun playPause() {
        postJob?.cancel()
        skipNoti()
        action = when(uiState.value.state){
            "stopped" -> "play"
            "playing" -> "pause"
            else -> "resume"
        }
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionBool(
                    actionName = action.toString(),
                    deviceID = uiState.value.id
                )
            }.onFailure {
                postJob = null
            }
        }
        _speakerUiState.update { currentState ->
            when (uiState.value.state) {
                "playing" -> currentState.copy(state = "paused")
                else -> currentState.copy(state = "playing")
            }
        }
        postJob = null
    }

    fun setVolume(vol: Int,skip: Boolean? = true){
        postJob?.cancel()
        skipNoti(skip)
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
        skipNoti()
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
            skipNoti()
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
            skipNoti()
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
            skipNoti()
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


        fun genreSet(g: String,skip: Boolean? = true) {
            postJob?.cancel()
            skipNoti(skip)
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