package com.example.hci_mobileapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class SmartHomeScreens(val title: String, val icon: ImageVector, val route :String) {
    object RecentScreen: SmartHomeScreens("Recent", Icons.Filled.List, "recent_screen")
    object DeviceScreen: SmartHomeScreens("Devices", Icons.Filled.Home, "device_screen")
}