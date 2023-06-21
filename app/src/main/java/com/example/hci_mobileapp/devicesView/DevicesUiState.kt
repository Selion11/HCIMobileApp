package com.example.hci_mobileapp.devicesView

import com.example.hci_mobileapp.data.network.model.AllDevices
import com.example.hci_mobileapp.data.network.model.ApiDevice

data class DevicesUiState(
    val devices: List<ApiDevice>? = null,
    val isLoading: Boolean = false,
    val MSG: String? = null
)

val DevicesUiState.hasError : Boolean get() = MSG != null