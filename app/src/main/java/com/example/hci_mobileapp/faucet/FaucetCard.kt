package com.example.hci_mobileapp.faucet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.R
import com.example.hci_mobileapp.data.network.model.ApiDevice


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaucetCard(
    faucetViewModel: FacuetViewModel = viewModel(),
   data: ApiDevice
){
    val faucetUiState = faucetViewModel.uiState.collectAsState()

    faucetViewModel.nameSet(data.name.toString())
    faucetViewModel.setId(data.id.toString())
    
    var dispVal by remember { mutableStateOf("0") }
    
    var dispenseDialog by  remember { mutableStateOf(false) }
    
    var unitsDialog by remember { mutableStateOf( false) } 
    
    val units = stringArrayResource(faucetUiState.value.units)
    
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
                    text = faucetUiState.value.name,
                    fontSize = 8.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
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

                IconButton(onClick = {dispenseDialog = true  },
                    enabled = faucetUiState.value.state == R.string.On
                ) {
                    Icon(
                        painter = painterResource(faucetUiState.value.icons.disp),
                        contentDescription = null
                    )
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
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedTextField(
                            value = dispVal,
                            onValueChange = {dispVal = it},
                            singleLine = true
                        )
                    }
                    Row(){
                        TextButton(onClick = { unitsDialog = true }) {
                            Icon(painter = painterResource(id = faucetUiState.value.icons.disp)
                                , contentDescription = null)
                        }
                        TextButton(onClick = {  }
                        ){
                            Icon(
                                painter = painterResource(faucetUiState.value.icons.disp),
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(faucetUiState.value.actions.dispense)
                            )
                        }
                        TextButton(
                                onClick = {
                                    dispenseDialog = false
                                },
                        ) {
                        Text(stringResource(id = R.string.confirmation))
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
                                onClick = { faucetViewModel.setDispenseUnit(item) },
                                modifier = Modifier.padding(start = 1.dp, end = 1.dp)
                            ) {
                                Text(item)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
                    TextButton(
                        onClick = {
                            unitsDialog = false
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
fun prevFau(){
    FaucetCard(name = "Maria")
}
*/
