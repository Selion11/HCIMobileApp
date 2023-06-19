package com.example.hci_mobileapp.faucet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.R
import com.example.hci_mobileapp.data.network.RetrofitClient
import com.example.hci_mobileapp.data.network.model.ApiDevice
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FacuetViewModel(device: ApiDevice) : ViewModel() {

    private val _faucetUiState = MutableStateFlow(FaucetUiState())

    init {
        _faucetUiState.value = FaucetUiState(
            name = device.name,
            id = device.id
        )
    }

    val uiState: StateFlow<FaucetUiState> = _faucetUiState.asStateFlow()

    private var postJob: Job? = null


    private var action: String? = null

    fun dispense(value: Int){
        action = "dispense"
        postJob =  viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionMixed(
                    actionName = action,
                    deviceID = uiState.value.id,
                    quantity = value,
                    units = uiState.value.dispenseUnits
                )
            }.onFailure {
                /*Thorw Notification to user*/
            }
        }
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

    fun turnOnOff(){
        /*no voy a cambiar todo por decir open o close muchachos tengamos media neurona*/
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