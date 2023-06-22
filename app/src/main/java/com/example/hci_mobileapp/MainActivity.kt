package com.example.hci_mobileapp

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem


import android.os.Bundle

import android.Manifest
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
import androidx.navigation.compose.*
import androidx.navigation.*
import com.example.hci_mobileapp.devicesView.DevicesViewModel
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
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HCIMobileAppTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController = navController) },
                    containerColor = Color.Transparent
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier.padding(paddingValues),
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
                        SmartHomeNavGraph(navController = navController,deviceModel)
                    }
                }
            }
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



@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        SmartHomeScreens.RecentScreen,
        SmartHomeScreens.DeviceScreen
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}


/*@Composable
fun dummyView() {
    Row(verticalAlignment = Alignment.Top) {
        Text(text = "Title Placeholder", textAlign = TextAlign.Center)
    }
    Row(modifier = Modifier.padding(top = 50.dp), horizontalArrangement = Arrangement.Center) {
        renderDevices()
    }
}*/


