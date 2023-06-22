package com.example.hci_mobileapp.devicesView

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.MainActivity
import com.example.hci_mobileapp.data.network.RetrofitClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.timerTask

class DevicesViewModel(): ViewModel(){

    private val _devicesUiState = MutableStateFlow(DevicesUiState())

    val uiState : StateFlow<DevicesUiState> = _devicesUiState.asStateFlow()

    private var fetchJob: Job? = null


    init {
        fetchAllDevices()
    }

    val timerTask = Timer().schedule(timerTask { fetchAllDevices() },5000, 10000)


    @SuppressLint("StaticFieldLeak")
    private lateinit var mainActivity: MainActivity

    fun setMainActivity(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
    }

    // Use the mainActivity reference to call its functions
    @RequiresApi(Build.VERSION_CODES.O)
    fun notifGenerate(id: String) {
        mainActivity.generateNotif(id)
    }



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