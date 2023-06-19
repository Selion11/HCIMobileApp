package com.example.hci_mobileapp.ac

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.R
import com.example.hci_mobileapp.data.network.RetrofitClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AcViewModel : ViewModel(){
    private val _acUiState = MutableStateFlow(AcUiState())

    val uiState: StateFlow<AcUiState> = _acUiState.asStateFlow()

    private var postJob: Job? = null

    private var action: String? = null

    fun nameSet(nameToChange: String?) {
        if(nameToChange != null)
            _acUiState.update { currentState ->
                currentState.copy(name = nameToChange)
            }
    }

    fun idSet(ID: String?) {
        if(ID != null)
            _acUiState.update { currentState ->
                currentState.copy(id = ID)
            }
    }

    fun setTemp(temp : Int){
        action =  "setTemperature"
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionInt(
                    actionName = action.toString(),
                    deviceID = uiState.value.id,
                    params = temp
                )
            }.onFailure {
                /*Thorw Notification to user*/
            }.onSuccess {
                _acUiState.update { currentState ->
                    currentState.copy(temperature = temp)
                }
            }
        }

    }

    fun iconSelection(): Int {
        return if (uiState.value.state == R.string.Off) {
            R.drawable.airoff
        } else
            R.drawable.airon
    }

    fun modeSwitch(mode: String){
        action = "setMode"
        postJob = viewModelScope.launch {
           runCatching {
               val modeToSend = when(mode){
                   "Cool" -> "cool"
                   "Frío" -> "cool"
                   "Heat" -> "heat"
                   "Calor" -> "heat"
                   else -> "fan"
               }
               RetrofitClient.getApiService().doActionString(
                   actionName = action.toString(),
                   deviceID = uiState.value.id,
                   params =  modeToSend
               )
            }
        }
        _acUiState.update { currentState ->
            currentState.copy(mode = mode)
        }
    }

    fun speedChange(speed: String){
        action = "setFanSpeed"
        postJob = viewModelScope.launch {
            runCatching {
                val modeToSend = when(speed){
                    "Automatic" -> "auto"
                    "Automático" -> "auto"
                    else -> speed
                }
                RetrofitClient.getApiService().doActionString(
                    actionName = action.toString(),
                    deviceID = uiState.value.id,
                    params =  modeToSend
                )
            }
        }
        _acUiState.update { currentState ->
            currentState.copy(speed = speed)
        }
    }

    fun horiSwingChange(value: String){
        action = "setHorizontalSwing"
        postJob = viewModelScope.launch {
            runCatching {
                val modeToSend = when(value){
                    "Automatic" -> "auto"
                    "Automático" -> "auto"
                    else -> value
                }
                RetrofitClient.getApiService().doActionString(
                    actionName = action.toString(),
                    deviceID = uiState.value.id,
                    params =  modeToSend
                )
            }
        }
        _acUiState.update { currentState ->
            currentState.copy(horVal = value)
        }
    }

    fun vertSwingChange(value: String){
        action = "setVerticalSwing"
        postJob = viewModelScope.launch {
            runCatching {
                val modeToSend = when(value){
                    "Automatic" -> "auto"
                    "Automático" -> "auto"
                    else -> value
                }
                RetrofitClient.getApiService().doActionString(
                    actionName = action.toString(),
                    deviceID = uiState.value.id,
                    params =  modeToSend
                )
            }
        }
        _acUiState.update { currentState ->
            currentState.copy(vertValue = value)
        }
    }

    fun turnOnOff(){
        _acUiState.update { currentState ->
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
}