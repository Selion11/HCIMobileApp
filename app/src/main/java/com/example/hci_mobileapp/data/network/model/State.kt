package com.example.hci_mobileapp.data.network.model

import com.google.gson.annotations.SerializedName


data class State (

    @SerializedName("status"            ) var status            : String?   = null,
    @SerializedName("color"             ) var color             : String?   = null,
    @SerializedName("brightness"        ) var brightness        : Int?      = null,
    @SerializedName("volume"            ) val volume            : Int?      = null,
    @SerializedName("genre"             ) val genre             : String?   = null,
    @SerializedName("temperature"       ) val temperature       : Int?      = null,
    @SerializedName("mode"              ) val mode              : String?   = null,
    @SerializedName("verticalSwing"     ) val verticalSwing     :  String?   = null,
    @SerializedName("horizontalSwing"   ) val horizontalSwing   : String?   = null,
    @SerializedName("fanSpeed"          ) val fanSpeed          : String?   = null,
)