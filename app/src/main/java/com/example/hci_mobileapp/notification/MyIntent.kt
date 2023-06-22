package com.example.hci_mobileapp.notification

class MyIntent(deviceID : String) {
    var name = deviceID
    companion object {
        const val SHOW_NOTIFICATION = "com.example.hci_mobileapp.notification.SHOW_NOTIFICATION"
        const val DEVICE_ID = "com.example.hci_mobileapp.DEVICE_ID"
        const val PACKAGE = "com.example.hci_mobileapp"
    }
}