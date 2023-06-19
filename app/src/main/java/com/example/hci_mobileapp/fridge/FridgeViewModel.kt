package com.example.hci_mobileapp.fridge

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FridgeViewModel : ViewModel() {

    private val _fridgeUiState = MutableStateFlow(FridgeUiState())

    val uiState: StateFlow<FridgeUiState> = _fridgeUiState.asStateFlow()

    fun nameSet(nameToChange: String?){
        if(nameToChange != null)
        _fridgeUiState.update { currentState ->
            currentState.copy(name = nameToChange)
        }
    }

    fun setid(ID: String?){
        if(ID != null)
        _fridgeUiState.update { currentState ->
            currentState.copy(id = ID)
        }
    }

    fun modeSet(modeToSet: String){
        _fridgeUiState.update { currentState ->
            currentState.copy(curMode = modeToSet)
        }
    }
}