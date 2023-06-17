package com.example.hci_mobileapp.data.network.model

import com.google.gson.annotations.SerializedName

data class ApiDevice (

    @SerializedName("id"    ) var id    : String? = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("type"  ) var type  : Type?   = Type(),
    @SerializedName("state" ) var state : State?  = State(),
    @SerializedName("meta"  ) var meta  : Meta?   = Meta()

)