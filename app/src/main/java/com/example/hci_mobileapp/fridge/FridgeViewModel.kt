package com.example.hci_mobileapp.fridge

import androidx.lifecycle.ViewModel
import com.example.hci_mobileapp.data.network.model.ApiDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FridgeViewModel(device: ApiDevice) : ViewModel() {

    private val _fridgeUiState = MutableStateFlow(FridgeUiState())
    init {
        _fridgeUiState.value = FridgeUiState(
            name = device.name,
            id = device.id
        )
    }

    val uiState: StateFlow<FridgeUiState> = _fridgeUiState.asStateFlow()

    fun modeSet(modeToSet: String){
        _fridgeUiState.update { currentState ->
            currentState.copy(curMode = modeToSet)
        }
    }
}