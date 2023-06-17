package com.example.hci_mobileapp.faucet

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

class FacuetViewModel : ViewModel() {
    private val _faucetUiState = MutableStateFlow(FaucetUiState())

    val uiState: StateFlow<FaucetUiState> = _faucetUiState.asStateFlow()

    private var postJob: Job? = null


    private var action: String? = null

    fun nameSet(nameToChange: String) {
        _faucetUiState.update { currentState ->
            currentState.copy(name = nameToChange)
        }
    }

    fun dispense(value: Int): String{
        return "Ok"
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

    fun setId(ID: String){
        _faucetUiState.update {currentState->
            currentState.copy(id = ID)
        }
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
            "turnOff"
        }else
            "turnOn"

        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doAction(
                    actionName = action.toString(),
                    deviceID = uiState.value.id.toString())
            }.onFailure {
                /*Thorw Notification to user*/
            }
        }
    }
}