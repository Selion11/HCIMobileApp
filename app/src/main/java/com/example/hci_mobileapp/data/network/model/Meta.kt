package com.example.hci_mobileapp.data.network.model

import com.google.gson.annotations.SerializedName


data class Meta(val id: String? = null){
    @SerializedName("meta"      ) var meta: String? = null
}