package com.example.hci_mobileapp.fridge

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.R
import com.example.hci_mobileapp.data.network.model.ApiDevice


@Composable
fun FridgeCard(
    fridgeViewModel: FridgeViewModel = viewModel(),
    data: ApiDevice
){


    val fridgeState = fridgeViewModel.uiState.collectAsState()

    val dialogOpen = remember { mutableStateOf(false) }

    val modesOpen = remember { mutableStateOf(false) }

    val modes = stringArrayResource(id = fridgeState.value.modes)

    fridgeViewModel.nameSet(data.name.toString())
    fridgeViewModel.setid(data.id.toString())

    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.width(350.dp),
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
                    text = fridgeState.value.name,
                    fontSize = 8.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
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
                    fontSize = 9.sp,
                    modifier = Modifier
                        .padding(horizontal = 7.dp)

                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 9.dp)
                .height(55.dp)
                .width(70.dp),
            horizontalArrangement = Arrangement
                .Center
        ){
            TextButton(onClick = { modesOpen.value = true },
                border = BorderStroke(width = 2.dp, color = Color.Black),
                shape = MaterialTheme.shapes.medium) {
                Icon(painter = painterResource(fridgeState.value.icons.tune),
                    contentDescription = null,
                    modifier = Modifier,
                    tint = Color.Black)
                Text(text = "Mode", color = Color.Black)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 9.dp)
                .height(55.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { dialogOpen.value = true },
                modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    painter = painterResource(fridgeState.value.icons.more),
                    contentDescription = null
                )
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
                    text = stringResource(id = R.string.modeMSG))
                Row() {
                    LazyRow(){
                        items(items = modes){
                                item ->
                            TextButton(
                                onClick = { fridgeViewModel.modeSet(item) },
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
}




/*
@Preview
@Composable
fun CardPrev(){
    FridgeCard(name = "Ayo")
}

*/

