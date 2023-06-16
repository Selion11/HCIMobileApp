package com.example.hci_mobileapp.ac

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.hci_mobileapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AcViewModel : ViewModel(){
    private val _acUiState = MutableStateFlow(AcUiState())

    val uiState: StateFlow<AcUiState> = _acUiState.asStateFlow()

    fun nameSet(nameToChange: String) {
        _acUiState.update { currentState ->
            currentState.copy(name = nameToChange)
        }
    }

    fun idSet(id: String) {
        _acUiState.update { currentState ->
            currentState.copy(id = id)
        }
    }

    fun iconSelection(): Int {
        return if (uiState.value.state == R.string.Off) {
            R.drawable.airoff
        } else
            R.drawable.airon
    }

    fun modeSwitch(mode: String){
        _acUiState.update { currentState ->
            currentState.copy(mode = mode)
        }
    }

    fun speedChange(speed: Int){
        _acUiState.update { currentState ->
            currentState.copy(speed = speed)
        }
    }

    fun horiSwingChange(value: String){
        _acUiState.update { currentState ->
            currentState.copy(horVal = value)
        }
    }

    fun vertSwingChange(value: String){
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
    }
}