package com.example.hci_mobileapp.ac

// TextField(value = "ingrese nombre", onValueChange = {acViewModel.nameSet(it)})

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcCard(
    acViewModel: AcViewModel = viewModel(),
    name:String,
    id: String
) {
    val acUiState = acViewModel.uiState.collectAsState()

    acViewModel.nameSet(name)
    acViewModel.idSet(id)

    var modeDialog = remember {
        mutableStateOf(false)
    }

    val optionDialog = remember {
        mutableStateOf(false)
    }

    val horDialog = remember {
        mutableStateOf(false)
    }

    val vertDialog = remember {
        mutableStateOf(false)
    }

    val modes = stringArrayResource(acUiState.value.arrays.AcModes)

    val horValues = stringArrayResource(acUiState.value.arrays.horValues)

    val verValues = stringArrayResource(acUiState.value.arrays.vertValues)

    fun openOptions() {
        if (acUiState.value.state == R.string.On) optionDialog.value = true
    }

    fun doAndCloseMode(mode: String) {
        acViewModel.modeSwitch(mode)
        modeDialog.value = false
    }

    fun doAndCloseHor(mode: String) {
        acViewModel.horiSwingChange(mode)
        horDialog.value = false
    }

    fun doAndCloseVert(mode: String) {
        acViewModel.vertSwingChange(mode)
        vertDialog.value = false
    }

    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(width = 2.dp, color = Color.Black),
        onClick = { openOptions() },
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
                    text = acUiState.value.name + acUiState.value.mode,
                    fontSize = 8.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
            Row {
                IconButton(onClick = { acViewModel.turnOnOff() }) {
                    Icon(
                        painter = painterResource(acViewModel.iconSelection()),
                        contentDescription = stringResource(R.string.ac),
                        modifier = Modifier.size(45.dp)
                        )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 45.dp),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(180.dp)
                    .padding(10.dp)
                    .height(55.dp)
            ) {

                TextButton(
                    onClick = { modeDialog.value = true },
                    enabled = acUiState.value.state == R.string.On,
                    modifier = Modifier.padding(start = 40.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                ) {
                    Icon(
                        painter = painterResource(acUiState.value.icons.mode),
                        contentDescription = null
                    )
                    Text(text = stringResource(acUiState.value.actions.mode))
                }
            }
        }
    }

    if (modeDialog.value) {
        Dialog(onDismissRequest = { modeDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {
                Text(
                    text = stringResource(id = R.string.modeMSG),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.padding(top = 25.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(items = modes) { item ->
                            TextButton(
                                onClick = { doAndCloseMode(item) },
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

    if (optionDialog.value) {
        Dialog(onDismissRequest = { optionDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 5.dp)
                ) {
                    TextButton(onClick = { horDialog.value = true }) {
                        Row() {
                            Icon(
                                painter = painterResource(acUiState.value.icons.horSwing),
                                contentDescription = null
                            )
                        }
                        Row() {
                            Text(text = stringResource(acUiState.value.actions.horSwing))
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(top = 35.dp)
                ) {
                    TextButton(onClick = { vertDialog.value = true }) {
                        Row() {
                            Icon(
                                painter = painterResource(acUiState.value.icons.vertSwing),
                                contentDescription = null
                            )
                        }
                        Row() {
                            Text(text = stringResource(acUiState.value.actions.vertSwing))
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(top = 65.dp)
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Row() {
                            Icon(
                                painter = painterResource(acUiState.value.icons.fanSpeed),
                                contentDescription = null
                            )
                        }
                        Row() {
                            Text(text = stringResource(acUiState.value.actions.fanSpeed))
                        }
                    }
                }
            }
        }
    }

    if(horDialog.value){
        Dialog(onDismissRequest = { horDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {
                Text(
                    text = stringResource(id = R.string.horSwingMSg),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.padding(top = 25.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(items = horValues) { item ->
                            TextButton(
                                onClick = { doAndCloseHor(item) },
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

    if(vertDialog.value) {
        Dialog(onDismissRequest = { horDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {
                Text(
                    text = stringResource(id = R.string.vertSwingMsg),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.padding(top = 25.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(items = verValues) { item ->
                            TextButton(
                                onClick = { doAndCloseVert(item) },
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

@Composable
@Preview
fun acPrev(){
    AcCard(name ="juan", id = "11")
}