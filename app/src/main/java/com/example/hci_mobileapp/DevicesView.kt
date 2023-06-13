package com.example.hci_mobileapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.hci_mobileapp.speaker.SpeakerCard


@OptIn(ExperimentalMaterial3Api::class)
private val devices = listOf(
   "Hello","Goodbye","I am Narnia"
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun renderDevices(
    //modifier: Modifier = Modifier
){

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)){
        items(items = devices){item -> SpeakerCard(name = item) }
    }
}

/*
@Preview
@Composable
fun devPrev(){
    renderDevices()
}*/
