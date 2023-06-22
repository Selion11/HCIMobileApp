package com.example.hci_mobileapp.devicesView

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.MainActivity
import com.example.hci_mobileapp.data.network.RetrofitClient
import com.example.hci_mobileapp.data.network.model.AllDevices
import com.example.hci_mobileapp.data.network.model.ApiDevice
import com.example.hci_mobileapp.notification.MyApplication
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

class DevicesViewModel: ViewModel(){

    private val _devicesUiState = MutableStateFlow(DevicesUiState())

    val uiState : StateFlow<DevicesUiState> = _devicesUiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetchAllDevices()
    }

    val timerTask = Timer().schedule(timerTask { fetchAllDevices() },5000, 10000)


    /*fun dismissMessage(){
        _devicesUiState.update { currentState ->
            currentState.copy(MSG = null)
        }
    }*/

    fun fetchAllDevices(){
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            _devicesUiState.update {currentState ->
                currentState.copy(isLoading = true)
            }

           runCatching {
               RetrofitClient.getApiService().getAllDevices()
           }.onSuccess {response ->
               _devicesUiState.update {currentState ->
                   currentState.copy(isLoading = false, devices = response.body()?.devicesList)
               }
           }.onFailure {e ->
               _devicesUiState.update {currentState ->
                   currentState.copy(isLoading = false, MSG = e.message)
               }
           }
        }
    }
}