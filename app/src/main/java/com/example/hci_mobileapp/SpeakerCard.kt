package com.example.hci_mobileapp

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp


@ExperimentalMaterial3Api
@Composable
fun SpeakerCard(){
    val brusher = SolidColor(Color.Red)

    Card(
        Modifier.size(width = 180.dp, height = 100.dp),
        border = BorderStroke(2.dp, brush = brusher),
    ){
        Column() {
            Text(text = "Carne con pollo")
            Row() {
                Button(onClick = { }) {
                    Text(text = "Hola")
            }
                Button(onClick = {}) {
                    Text(text = "Yeso")
                }

        }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CardPrev(){
    SpeakerCard()
}