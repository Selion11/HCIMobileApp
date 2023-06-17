package com.example.hci_mobileapp.devicesView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.data.network.RetrofitClient
import com.example.hci_mobileapp.data.network.model.AllDevices
import com.example.hci_mobileapp.data.network.model.ApiDevice
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DevicesViewModel: ViewModel(){

    private val _devicesUiState = MutableStateFlow(DevicesUiState())

    val uiState : StateFlow<DevicesUiState> = _devicesUiState.asStateFlow()

    val devices: List<ApiDevice>? = null

    private var fetchJob: Job? = null
/*
    @Composable
    fun getAllDeivces(
        data: Device
    ): {
        return null
    }
*/

    fun dismissMessage(){
        _devicesUiState.update { currentState ->
            currentState.copy(MSG = null)
        }
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
                   currentState.copy(isLoading = false, devices = response.body())
               }
           }.onFailure {e ->
               _devicesUiState.update {currentState ->
                   currentState.copy(isLoading = false, MSG = e.message)
               }
           }
        }
    }

    fun check(id:String): String {
        when(id){
            "c89b94e8581855bc" -> return "Speaker"
            "li6cbv5sdlatti0j" -> return "AC"
            "rnizejqr2di0okho" -> return "Fridge"
            "dbrlsh7o5sn8ur4i" -> return "Faucet"
            "go46xmbqeomjrsjr" -> return "Lamp"
            else -> return "Not found"
        }
    }

}