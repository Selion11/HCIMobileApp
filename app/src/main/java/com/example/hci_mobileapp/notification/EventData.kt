package com.example.hci_mobileapp.notification

import com.google.gson.annotations.SerializedName

data class EventData(
    @SerializedName("timestamp") var timestamp: String,
    @SerializedName("deviceId") var deviceId: String,
    @SerializedName("event") var event: String,
    @SerializedName("args") var args: Map<String, String> = mapOf()
)
