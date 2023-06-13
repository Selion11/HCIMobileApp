package com.example.hci_mobileapp.speaker

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.hci_mobileapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpeakerViewModel : ViewModel() {
    private val _speakerUiState = MutableStateFlow(SpeakerUiState())

    val uiState :StateFlow<SpeakerUiState> = _speakerUiState.asStateFlow()

    fun nameSet(nameToChange: String){
        _speakerUiState.update { currentState ->
            currentState.copy(name = nameToChange)

        }
    }

    @Composable
    fun turnOnOff(){
        _speakerUiState.update { currentState ->
            if (uiState.value.state.equals(R.string.Off))
                currentState.copy(state = stringResource(id = R.string.On))
            else
                currentState.copy(state = stringResource(id = R.string.Off))
        }
    }

    fun genreSet(genre: String) {
        _speakerUiState.update { currentState ->
            currentState.copy(currGen = genre)

        }
    }
}