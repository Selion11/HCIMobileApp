package com.example.hci_mobileapp.lamp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.R
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.hci_mobileapp.data.network.model.ApiDevice

@Composable
fun LampCard(
    lampViewModel: LampViewModel = viewModel(),
    data: ApiDevice
){
    val lampUiState = lampViewModel.uiState.collectAsState()

    lampViewModel.nameSet(data.name.toString())
    lampViewModel.setid(data.id.toString())

    val intensityDialog = remember { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(width = 2.dp, color = Color.Black),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 3.dp,
                    vertical = 4.dp
                ),
        ) {
            Row {
                Text(
                    text = lampUiState.value.name,
                    fontSize = 8.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
            Row {
                IconButton(onClick = {lampViewModel.turnOnOff() }) {
                    Icon(
                        painter = painterResource(lampViewModel.iconSelection()),
                        contentDescription = stringResource(R.string.lamp),
                        modifier = Modifier.size(45.dp),

                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 45.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(180.dp)
                    .padding(10.dp)
                    .height(55.dp)
            ) {

                IconButton(onClick = {/* Open dialog and picker to select value
                      send that value to color set*/  },
                    enabled = lampUiState.value.state == R.string.On
                ) {
                    Icon(
                        painter = painterResource(lampUiState.value.icons.colorPicker),
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = { intensityDialog.value = true },
                    enabled = lampUiState.value.state == R.string.On
                ) {
                    Icon(
                        painter = painterResource(lampUiState.value.icons.intense),
                        contentDescription = null,
                    )
                }
            }
        }
    }

    if(intensityDialog.value){
        Dialog(onDismissRequest = { intensityDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ){
                Text(text = lampUiState.value.intensity.toInt().toString())

                Row() {
                    IconButton(onClick = {
                        lampViewModel.setIntensity(lampUiState.value.intensity - 1)
                    }) {
                        Icon(painter = painterResource(id = R.drawable.minus),
                            contentDescription = null)
                    }
                    Slider(value = lampUiState.value.intensity.toInt().toFloat(),
                        onValueChange = {lampViewModel.setIntensity(it)},
                        valueRange = 0f..10f,
                    modifier = Modifier.width(240.dp))
                    IconButton(onClick = {
                        lampViewModel.setIntensity(lampUiState.value.intensity + 1)
                    }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_add_24),
                            contentDescription = null)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
                    TextButton(
                        onClick = {
                           intensityDialog.value = false
                        },
                    ) {
                        Text(stringResource(id = R.string.confirmation))
                    }
                }
            }
        }
     }
}

/*
@Preview
@Composable
fun prev(){
    LampCard(name = "juenaso")
}
*/
