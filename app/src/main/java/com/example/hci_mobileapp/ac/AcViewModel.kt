package com.example.hci_mobileapp.ac


import android.content.Intent
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
class AcViewModel(device: ApiDevice, parent: DevicesViewModel) : ViewModel(){
    private val _acUiState = MutableStateFlow(AcUiState())
    private val par = parent
    fun skipNoti(){
        uiState.value.id?.let { par.notifGenerate(it) }
    }
    init {
        _acUiState.value = AcUiState(
            name = device.name,
            id = device.id,
            state = if(device.state?.status == "on"){
                R.string.On
            }else R.string.Off
        )
        device.state?.temperature?.let { setTemp(it) }
        device.state?.mode?.let { modeSwitch(it) }
        device.state?.fanSpeed?.let { speedChange(it) }
        device.state?.verticalSwing?.let { vertSwingChange(it) }
        device.state?.horizontalSwing?.let { horiSwingChange(it) }
    }

    val uiState: StateFlow<AcUiState> = _acUiState.asStateFlow()

    private var postJob: Job? = null

    private var action: String? = null




    fun setTemp(temp: Int){
        postJob?.cancel()
        skipNoti()
        action =  "setTemperature"
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionInt(
                    actionName = action.toString(),
                    deviceID = uiState.value.id,
                    params = listOf(temp)
                )
            }.onFailure {
            /*Thorw Notification to user*/
            }.onSuccess {
            }
        }
        _acUiState.update { currentState ->
            currentState.copy(temperature = temp)
        }
    }

    fun iconSelection(): Int {
        return if (uiState.value.state == R.string.Off) {
            R.drawable.airoff
        } else
            R.drawable.airon
    }


    fun modeSwitch(mode: String){
        postJob?.cancel()
        skipNoti()
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
                   params =  listOf(modeToSend)
               )
            }
        }
        _acUiState.update { currentState ->
            currentState.copy(mode = mode)
        }
    }

    fun chooseSpeed(speed: String): String{
        return when(speed){
            "Automatic" -> "auto"
            "Automático" -> "auto"
            else -> speed
        }
    }

    fun speedChange(speed: String){
        postJob?.cancel()
        skipNoti()
        action = "setFanSpeed"
        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionString(
                    actionName = action.toString(),
                    deviceID = uiState.value.id,
                    params =  listOf( chooseSpeed(speed))
                )
            }
        }
        _acUiState.update { currentState ->
            currentState.copy(speed = speed)
        }
        postJob = null
    }


    fun horiSwingChange(value: String){
        postJob?.cancel()
        skipNoti()
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
                    params =  listOf(modeToSend)
                )
            }
        }
        _acUiState.update { currentState ->
            currentState.copy(horVal = value)
        }
    }


    fun vertSwingChange(value: String){
        postJob?.cancel()
        skipNoti()
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
                    params =  listOf(modeToSend)
                )
            }
        }
        _acUiState.update { currentState ->
            currentState.copy(vertValue = value)
        }
    }




    fun turnOnOff(){
        postJob?.cancel()
        skipNoti()
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