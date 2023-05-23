package com.example.hci_mobileapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@ExperimentalMaterial3Api
@Composable
fun SpeakerCard(name : String){

    Card(){
        Text(text = name)


        Row(){
            Button(onClick = { }/*, modifier = Modifier.width(100.dp)*/) {
                Text(text = "More data")
            }

            Divider(modifier = Modifier.width(10.dp))

            Button(onClick = { }/*, modifier = Modifier.width(120.dp)*/) {
                Text(text = "Less data")
            }
        }
    }








}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CardPrev(){
    SpeakerCard("Speaker 1")
}