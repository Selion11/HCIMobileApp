package com.example.hci_mobileapp.lamp

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.hci_mobileapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LampViewModel: ViewModel() {
    private val _lampUiState = MutableStateFlow(LampUiState())


    val uiState: StateFlow<LampUiState> = _lampUiState.asStateFlow()

    fun nameSet(nameToChange: String){
        _lampUiState.update { currentState ->
            currentState.copy(name = nameToChange)
        }
    }


    fun iconSelection(): Int {
        return if (uiState.value.state == R.string.Off) {
            R.drawable.outline_lightbulb_24
        } else
            R.drawable.baseline_lightbulb_24
    }

    @Composable
    fun TurnOnOff(){
        _lampUiState.update { currentState ->
            if (uiState.value.state == (R.string.Off))
                currentState.copy(state =  R.string.On)
            else
                currentState.copy(state =  R.string.Off)
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