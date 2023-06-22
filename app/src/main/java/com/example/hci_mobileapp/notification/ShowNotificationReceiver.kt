package com.example.hci_mobileapp.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hci_mobileapp.MainActivity
import com.example.hci_mobileapp.R
import com.example.hci_mobileapp.data.network.model.ApiDevice
import com.example.hci_mobileapp.speaker.SpeakerViewModel

class ShowNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent?.action == MyIntent.SHOW_NOTIFICATION) {
            val deviceId: String? = intent.getStringExtra(MyIntent.DEVICE_ID)
            Log.d(TAG, "Show notification intent received {$deviceId)")

            //showNotification(context, deviceId!!)
        }
    }

    fun showNotification(context: Context, device: ApiDevice) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MyIntent.DEVICE_ID, device.id)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )

        val builder = NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications)
            .setContentTitle(device.name.toString())
            .setStyle(
                NotificationCompat.BigTextStyle()
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .addAction( R.drawable.notifications,context.getString(R.string.confirmation),null)
            .setAutoCancel(false)


        try {
            val notificationManager = NotificationManagerCompat.from(context)
            if (notificationManager.areNotificationsEnabled())
                notificationManager.notify(device.id.hashCode(), builder.build())
        } catch (e: SecurityException) {
            Log.d(TAG, "Notification permission not granted $e")
        }
    }

    companion object {
        private const val TAG = "ShowNotificationReceiver"
    }
}