package com.example.hci_mobileapp.faucet

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
class FaucetViewModel(device: ApiDevice, parent: DevicesViewModel) : ViewModel() {

    private val _faucetUiState = MutableStateFlow(FaucetUiState())

    private val par = parent

    init {
        _faucetUiState.value = FaucetUiState(
            name = device.name,
            id = device.id,
            state = if(device.state?.status == "opened"){
                R.string.On
            }else R.string.Off
        )
    }

    val uiState: StateFlow<FaucetUiState> = _faucetUiState.asStateFlow()

    private var postJob: Job? = null

    private fun skipNoti(flag : Boolean? = true){
        if (flag == true){
            uiState.value.id?.let { par.notifGenerate(it) }
        }
    }

    private var action: String? = null

    fun dispenser(value: Int,skip: Boolean? = true){
        postJob?.cancel()
        skipNoti(skip)
        action = "dispense"
        postJob =  viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionMixed(
                    actionName = action,
                    deviceID = uiState.value.id,
                    params = arrayOf(value,uiState.value.dispenseUnits)
                )

            }.onFailure {
                /*Thorw Notification to user*/
            }
        }

        _faucetUiState.update { currentState ->
            if (uiState.value.state == (R.string.Off))
                currentState.copy(state =  R.string.On)
            else
                currentState.copy(state =  R.string.Off)
        }

        postJob = null
    }
    fun setDispenseUnit(units: String){
       _faucetUiState.update { currentState ->
           currentState.copy(dispenseUnits = units)
       }
    }

    fun iconSelection(): Int {
        return if (uiState.value.state == R.string.Off) {
            R.drawable.outline_water_drop_24
        } else
            R.drawable.baseline_water_drop_24
    }

    fun turnOnOff(skip: Boolean? = true){
        /*no voy a cambiar todo por decir open o close muchachos tengamos media neurona*/
        postJob?.cancel()
        skipNoti(skip)
        _faucetUiState.update { currentState ->
            if (uiState.value.state == (R.string.Off))
                currentState.copy(state =  R.string.On)
            else
                currentState.copy(state =  R.string.Off)
        }

        action = if(uiState.value.state ==(R.string.Off)){
            "close"
        }else
            "open"

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