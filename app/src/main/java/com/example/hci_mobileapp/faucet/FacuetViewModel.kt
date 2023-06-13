package com.example.hci_mobileapp.faucet

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.hci_mobileapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FacuetViewModel : ViewModel() {
    private val _faucetUiState = MutableStateFlow(FaucetUiState())

    val uiState: StateFlow<FaucetUiState> = _faucetUiState.asStateFlow()

    fun nameSet(nameToChange: String) {
        _faucetUiState.update { currentState ->
            currentState.copy(name = nameToChange)
        }
    }

    fun iconSelection(): Int {
        return if (uiState.value.state == R.string.Off) {
            R.drawable.outline_water_drop_24
        } else
            R.drawable.baseline_water_drop_24
    }


    fun turnOnOff(){
        _faucetUiState.update { currentState ->
            if (uiState.value.state == (R.string.Off))
                currentState.copy(state =  R.string.On)
            else
                currentState.copy(state =  R.string.Off)
        }
    }
}