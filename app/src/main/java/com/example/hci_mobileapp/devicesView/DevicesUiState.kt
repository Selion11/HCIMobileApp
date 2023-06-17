package com.example.hci_mobileapp.devicesView

import com.example.hci_mobileapp.data.network.model.AllDevices

data class DevicesUiState(
    val devices: AllDevices? = null,
    val isLoading: Boolean = false,
    val MSG: String? = null
)

val DevicesUiState.hasError : Boolean get() = MSG != null