package com.example.hci_mobileapp.data.network.model

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable


data class Playlist(

    @SerializedName("result" ) var result : List<Song> = listOf()

)
