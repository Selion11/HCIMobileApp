package com.example.hci_mobileapp.lamp

import androidx.compose.runtime.Composable
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

class LampViewModel: ViewModel() {
    private val _lampUiState = MutableStateFlow(LampUiState())

    val uiState: StateFlow<LampUiState> = _lampUiState.asStateFlow()

    private var postJob: Job? = null

    private var action: String? = null

    fun nameSet(nameToChange: String){
        _lampUiState.update { currentState ->
            currentState.copy(name = nameToChange)
        }
    }

    fun setid(ID: String){
        _lampUiState.update { currentState ->
            currentState.copy(id = ID)
        }
    }

    fun iconSelection(): Int {
        return if (uiState.value.state == R.string.Off) {
            R.drawable.outline_lightbulb_24
        } else
            R.drawable.baseline_lightbulb_24
    }


    fun turnOnOff(){
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

    fun colorSet(colorToSet: Long){
        _lampUiState.update { currentState ->
            currentState.copy(col = colorToSet)
        }
    }

    fun setIntensity(intensity: Float){
        _lampUiState.update { currentState ->
            currentState.copy(intensity = intensity)
        }
    }
}