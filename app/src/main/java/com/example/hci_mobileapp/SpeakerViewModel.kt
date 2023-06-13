package com.example.hci_mobileapp

import androidx.lifecycle.ViewModel
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
}