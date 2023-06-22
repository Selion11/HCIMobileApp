package com.example.hci_mobileapp.faucet

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.R


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaucetCard(
    faucetViewModel: FaucetViewModel = viewModel(),
){
    val faucetUiState = faucetViewModel.uiState.collectAsState()

    
    var dispVal by remember { mutableStateOf("0") }
    
    var dispenseDialog by  remember { mutableStateOf(false) }
    
    var unitsDialog by remember { mutableStateOf( false) } 
    
    val units = stringArrayResource(faucetUiState.value.units)

    fun closeUnits(units: String){
        faucetViewModel.setDispenseUnit(units)
        unitsDialog = false
    }
    
    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(width = 2.dp, color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(95.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 3.dp,
                    vertical = 4.dp
                ),
        ) {
            Row {
                faucetUiState.value.name?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                    )
                }
            }
            Row {
                IconButton(onClick = {faucetViewModel.turnOnOff() }) {
                    Icon(
                        painter = painterResource(faucetViewModel.iconSelection()),
                        contentDescription = stringResource(R.string.faucet),
                        modifier = Modifier.size(45.dp),

                        )
                }
            }
            Row {
                Text(
                    text = faucetUiState.value.dispenseUnits,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 45.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
                    .height(55.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

              TextButton(onClick = {dispenseDialog = true  },
                    enabled = faucetUiState.value.state == R.string.Off,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                ) {
                    Icon(
                        painter = painterResource(faucetUiState.value.icons.disp),
                        contentDescription = null
                    )
                    Text(text = stringResource(faucetUiState.value.actions.dispense))
                }
            }
        }
    }

    if(dispenseDialog){
        Dialog(onDismissRequest = { dispenseDialog = false}) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Slider(
                            value = dispVal.toFloat(),
                            onValueChange = { dispVal = it.toInt().toString() },
                            valueRange = 1f..100f,
                            modifier = Modifier.width(240.dp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(dispVal.toString() + " " + faucetUiState.value.dispenseUnits)
                    }
                    Row(){
                        TextButton(onClick = { unitsDialog = true }) {
                            Icon(painter = painterResource(id = faucetUiState.value.icons.disp)
                                , contentDescription = null)
                            Text(text = stringResource(R.string.units))
                        }
                        TextButton(
                            onClick = {
                                faucetViewModel.dispenser(dispVal.toInt())
                                dispenseDialog = false
                            }
                        ){
                            Icon(
                                painter = painterResource(faucetUiState.value.icons.disp),
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(faucetUiState.value.actions.dispense)
                            )
                        }
                    }
                }
            }
        }
    }
    
    if(unitsDialog){
        Dialog(onDismissRequest = { unitsDialog = false }) {

            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Row() {
                    LazyRow() {
                        items(items = units) { item ->
                            TextButton(
                                onClick = { closeUnits(item) },
                                modifier = Modifier.padding(start = 1.dp, end = 1.dp)
                            ) {
                                Text(item)
                            }
                        }
                    }
                }
            }
        }     
    } 
}

/*
@Preview
@Composable
fun prevFau(){
    FaucetCard(name = "Maria")
}
*/
