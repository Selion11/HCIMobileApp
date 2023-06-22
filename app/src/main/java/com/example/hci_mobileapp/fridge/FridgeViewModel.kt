package com.example.hci_mobileapp.fridge

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class FridgeViewModel(device: ApiDevice, parent: DevicesViewModel) : ViewModel() {

    private val _fridgeUiState = MutableStateFlow(FridgeUiState())

    private var postJob: Job? = null

    private var action: String? = null
    private val par = parent



    init {
        _fridgeUiState.value = FridgeUiState(
            name = device.name,
            id = device.id
        )

        device.state?.mode?.let { modeSet(it,false) }
        device.state?.freezerTemperature?.let { setFreezerTemp(it,false) }
        device.state?.temperature?.let { setTemp(it,false) }
    }

    private fun skipNoti(flag : Boolean? = true){
        if (flag == true){
            uiState.value.id?.let { par.notifGenerate(it) }
        }
    }

    val uiState: StateFlow<FridgeUiState> = _fridgeUiState.asStateFlow()

    fun modeSet(modeToSet: String,skip: Boolean? = true){
        action = "setMode"
        postJob?.cancel()
        skipNoti(skip)
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

    fun setFreezerTemp(temp: Int,skip: Boolean? = true){
        postJob?.cancel()
        skipNoti(skip)
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
    fun setTemp(temp: Int,skip: Boolean? = true){
        postJob?.cancel()
        skipNoti(skip)
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