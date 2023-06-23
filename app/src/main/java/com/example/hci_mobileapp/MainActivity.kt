package com.example.hci_mobileapp

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem


import android.os.Bundle

import android.Manifest
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.*
import androidx.navigation.*
import com.example.hci_mobileapp.devicesView.DevicesViewModel
import com.example.hci_mobileapp.devicesView.RenderDevices
import com.example.hci_mobileapp.notification.MyIntent
import com.example.hci_mobileapp.notification.SkipNotificationReceiver
import com.example.hci_mobileapp.ui.theme.HCIMobileAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    private lateinit var receiver: SkipNotificationReceiver

    private var deviceModel = DevicesViewModel()
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HCIMobileAppTheme {
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val permissionState =
                            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
                        if (!permissionState.hasPermission) {
                            NotificationPermission(permissionState = permissionState)
                            LaunchedEffect(true) {
                                permissionState.launchPermissionRequest()
                            }
                        }
                    }
                    deviceModel.setMainActivity(this)
                    RenderDevices(viewModel = deviceModel)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateNotif(id: String){
        receiver = SkipNotificationReceiver(id)
        IntentFilter(MyIntent.SHOW_NOTIFICATION)
            .apply { priority = 1 }
            .also {
                var flags = 0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    flags = Context.RECEIVER_NOT_EXPORTED
                registerReceiver(receiver, it, flags)
            }


    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        receiver = SkipNotificationReceiver(DEVICE_ID)
        IntentFilter(MyIntent.SHOW_NOTIFICATION)
            .apply {
                priority = 1
            }
            .also {
                var flags = 0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    flags = Context.RECEIVER_NOT_EXPORTED
                registerReceiver(receiver, it, flags)
            }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun NotificationPermission(
        permissionState: PermissionState,
    ) {
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = {  },
            permissionNotAvailableContent = {  }
        ) {

        }
    }
    companion object {
       var DEVICE_ID = " "
    }

}