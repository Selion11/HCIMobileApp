package com.example.hci_mobileapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.devicesView.DevicesViewModel

class SkipNotificationReceiver(private val deviceId: String) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
            if (intent.action.equals(MyIntent.SHOW_NOTIFICATION) &&
                intent.getStringExtra(MyIntent.DEVICE_ID).equals(deviceId)
            ) {
                Log.d(TAG, "Skipping notification send ($deviceId)")
                abortBroadcast()
            }

    }

    companion object {
        private const val TAG = "SkipNotificationReceiver"
    }
}