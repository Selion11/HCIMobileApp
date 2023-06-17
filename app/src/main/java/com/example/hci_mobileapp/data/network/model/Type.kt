package com.example.hci_mobileapp.data.network.model

import com.google.gson.annotations.SerializedName

class Type {
    @SerializedName("id"         ) var id         : String? = " "
    @SerializedName("name"       ) var name       : String? = " "
    @SerializedName("powerUsage" ) var powerUsage : Int?    = null
}