package com.example.hci_mobileapp.fridge

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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.R
import com.example.hci_mobileapp.data.network.model.ApiDevice


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FridgeCard(
    fridgeViewModel: FridgeViewModel = viewModel(),
){


    val fridgeState = fridgeViewModel.uiState.collectAsState()

    val dialogOpen = remember { mutableStateOf(false) }

    val modesOpen = remember { mutableStateOf(false) }

    val modes = stringArrayResource(id = fridgeState.value.modes)

    fun ModeAndClose(item: String){
        fridgeViewModel.modeSet(item)
        modesOpen.value = false
    }

    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(width = 2.dp, color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(95.dp),
        onClick = {dialogOpen.value = true}
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 3.dp,
                    vertical = 4.dp
                ),
        ) {
            Row {
                fridgeState.value.name?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                    )
                }
            }
            Row {
                Icon(
                    painter = painterResource(fridgeState.value.icons.fridge),
                    contentDescription = stringResource(R.string.fridge),
                    modifier = Modifier.size(45.dp)
                )

            }
            Row {
                Text(
                    text = "MODE: " + fridgeState.value.curMode,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp),

                )
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 45.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
                    .height(55.dp),
                horizontalArrangement = Arrangement
                    .Center
            ){
                TextButton(
                    onClick = { modesOpen.value = true },
                    modifier = Modifier.padding(start = 15.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent)
                ){
                    Icon(painter = painterResource(fridgeState.value.icons.tune),
                        contentDescription = null,
                        modifier = Modifier,
                        tint = Color.Black)
                    Text(text = stringResource(fridgeState.value.actions.mode))
                }
                TextButton(
                    onClick = { fridgeViewModel.setTemp(fridgeState.value.fridgeTemp - 1) },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = null)
                }
                Text(
                    text = fridgeState.value.fridgeTemp.toString(),
                    fontSize = 15.sp
                )
                TextButton(
                    onClick = { fridgeViewModel.setTemp(fridgeState.value.fridgeTemp + 1) },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_add_24),
                        contentDescription = null)
                }
            }
        }

    }

    if (modesOpen.value) {
        Dialog(
            onDismissRequest = { modesOpen.value = false }
        ) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {
                Text(
                    text = stringResource(id = R.string.modeMSG),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 11.dp, bottom = 25.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 13.dp)
                ) {
                    LazyRow(){
                        items(items = modes){
                                item ->
                            TextButton(
                                onClick = { ModeAndClose(item) },
                                modifier = Modifier.padding(start = 1.dp, end = 1.dp)
                            )   {
                                Text(item)
                            }
                        }
                    }
                }
                Row(verticalAlignment = Alignment.Bottom) {
                    TextButton(
                        onClick = {
                            modesOpen.value = false
                        }
                    ) {
                        Text(stringResource(id = R.string.confirmation))
                    }
                }
            }
        }
    }

    if (dialogOpen.value){

        Dialog(
            onDismissRequest = { dialogOpen.value = false }
        ) {
            Surface(  modifier = Modifier
                .width(365.dp)
                .height(90.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Text(
                    text = "Freezer",
                    modifier = Modifier
                        .padding(start = 15.dp)
                )
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp),
                        horizontalArrangement = Arrangement
                            .Center
                    ){
                        TextButton(
                            onClick = { fridgeViewModel.setFreezerTemp(fridgeState.value.freezerTemp - 1) },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.minus),
                                contentDescription = null)
                        }
                        Text(
                            text = fridgeState.value.freezerTemp.toString(),
                            fontSize = 15.sp
                        )
                        TextButton(
                            onClick = { fridgeViewModel.setFreezerTemp(fridgeState.value.freezerTemp + 1) },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_add_24),
                                contentDescription = null)
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
fun CardPrev(){
    FridgeCard(name = "Ayo")
}

*/

