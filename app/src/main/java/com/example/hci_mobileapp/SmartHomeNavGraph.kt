package com.example.hci_mobileapp


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hci_mobileapp.devicesView.DevicesViewModel
import com.example.hci_mobileapp.devicesView.RenderDevices


@Composable
fun SmartHomeNavGraph(navController: NavHostController, viewModel: DevicesViewModel) {
    NavHost(
        navController = navController,
        startDestination = SmartHomeScreens.RecentScreen.route
    ) {
        composable(SmartHomeScreens.RecentScreen.route) {
            // RecentView()
        }
        composable(SmartHomeScreens.DeviceScreen.route) {
            RenderDevices(viewModel = viewModel)
        }
    }
}