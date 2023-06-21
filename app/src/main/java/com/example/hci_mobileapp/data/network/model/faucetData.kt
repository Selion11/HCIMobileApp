package com.example.hci_mobileapp.data.network.model

import com.google.gson.annotations.SerializedName

class faucetData (
    @SerializedName("quantity"    ) var quantity    : Int? = null,
    @SerializedName("unit"  ) var unit  : String? = null,
)