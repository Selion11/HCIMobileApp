package com.example.hci_mobileapp.data.network.model

import com.google.gson.annotations.SerializedName


data class Playlist (

    @SerializedName("result" ) var result : List<Song> = listOf()

)