package com.example.hci_mobileapp.data.network.model

import com.google.gson.annotations.SerializedName

data class Song (
    @SerializedName("title"    ) var title    : String? = null,
    @SerializedName("artist"   ) var artist   : String? = null,
    @SerializedName("album"    ) var album    : String? = null,
    @SerializedName("duration" ) var duration : String? = null
)