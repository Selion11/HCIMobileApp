package com.example.hci_mobileapp.lamp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.R
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
class LampViewModel(device: ApiDevice, parent: DevicesViewModel): ViewModel() {

    private val _lampUiState = MutableStateFlow(LampUiState())

    private val par = parent



    init {
        _lampUiState.value = LampUiState(
            name = device.name,
            id = device.id,
            //col = device.state?.color,
            intensity = device.state?.brightness,
            state = if(device.state?.status == "opened"){
                R.string.On
            }else {
                R.string.Off
                  }
        )
    }

    private fun skipNoti(flag : Boolean? = true){
        if (flag == true){
            uiState.value.id?.let { par.notifGenerate(it) }
        }
    }

    val uiState: StateFlow<LampUiState> = _lampUiState.asStateFlow()

    private var postJob: Job? = null

    private var action: String? = null

    fun iconSelection(): Int {
        return if (uiState.value.state == R.string.Off) {
            R.drawable.outline_lightbulb_24
        } else
            R.drawable.baseline_lightbulb_24
    }


    fun turnOnOff(){
        postJob?.cancel()
        skipNoti()
        _lampUiState.update { currentState ->
            if (uiState.value.state == (R.string.Off))
                currentState.copy(state =  R.string.On)
            else
                currentState.copy(state =  R.string.Off)
        }

        action = if(uiState.value.state ==(R.string.Off)){
            "turnOff"
        }else
            "turnOn"
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionBool(
                    actionName = action.toString(),
                    deviceID = uiState.value.id)
            }.onFailure {
                /*Thorw Notification to user*/
            }
        }
    }

    fun colorSet(colorToSet: String){
        postJob?.cancel()
        skipNoti()
        action = "setColor"
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionString(
                    actionName = action.toString(),
                    deviceID = uiState.value.id,
                    params = listOf(colorToSet)
                )
            }
        }
        _lampUiState.update { currentState ->
            currentState.copy(col = colorToSet)
        }
    }

    fun currentColor(): String? {
        return uiState.value.col
    }

    fun setIntensity(intensity: Int){
        postJob?.cancel()
        skipNoti()
        action = "setBrightness"
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionInt(
                    actionName = action.toString(),
                    deviceID = uiState.value.id,
                    params = listOf(intensity)
                )
            }
        }
        _lampUiState.update { currentState ->
            currentState.copy(intensity = intensity)
        }
    }
}