package com.example.hci_mobileapp.fridge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci_mobileapp.data.network.RetrofitClient
import com.example.hci_mobileapp.data.network.model.ApiDevice
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FridgeViewModel(device: ApiDevice) : ViewModel() {

    private val _fridgeUiState = MutableStateFlow(FridgeUiState())

    private var postJob: Job? = null

    private var action: String? = null
    init {
        _fridgeUiState.value = FridgeUiState(
            name = device.name,
            id = device.id
        )

        modeSet("Default")
    }

    val uiState: StateFlow<FridgeUiState> = _fridgeUiState.asStateFlow()

    fun modeSet(modeToSet: String){
        action = "setMode"

        postJob = viewModelScope.launch {
            runCatching {
                RetrofitClient.getApiService().doActionString(
                    actionName = action,
                    deviceID = uiState.value.id,
                    params = listOf(
                        when(modeToSet){
                            "Party" -> "party"
                            "fiesta" -> "party"
                            "Vacation" -> "vacation"
                            "Vacaciones" -> "vacation"
                            else -> modeToSet.lowercase()
                        }
                    )
                )
            }.onFailure {
                /*Thorw Notification to user*/
            }.onSuccess {
            }
        }

        _fridgeUiState.update { currentState ->
            currentState.copy(curMode = modeToSet)
        }
    }

    fun setFreezerTemp(temp: Int){
        if(temp < -20 || temp > -8){
            //throw notif
        }else {
            action = "setFreezerTemperature"
            postJob = viewModelScope.launch {
                runCatching {
                    RetrofitClient.getApiService().doActionInt(
                        actionName = action,
                        deviceID = uiState.value.id,
                        params = listOf(temp)
                    )
                }.onFailure {
                    /*Thorw Notification to user*/
                }.onSuccess {
                }
            }
            _fridgeUiState.update { currentState ->
                currentState.copy(freezerTemp = temp)
            }
        }
    }
    fun setTemp(temp: Int){
        if(temp < 2 || temp > 8){
            //throw notif
        }else {
            action = "setTemperature"
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
            _fridgeUiState.update { currentState ->
                currentState.copy(fridgeTemp = temp)
            }
        }
    }
}