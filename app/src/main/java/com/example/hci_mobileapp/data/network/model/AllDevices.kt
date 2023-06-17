package com.example.hci_mobileapp.data.network.model

import com.google.gson.annotations.SerializedName

class AllDevices {

    @SerializedName("result" ) var devicesList : List<ApiDevice> = listOf()
}