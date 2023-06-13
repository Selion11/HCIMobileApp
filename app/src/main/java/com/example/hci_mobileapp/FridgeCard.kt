package com.example.hci_mobileapp

import android.icu.text.CaseMap.Title
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.DialogProperties

data class FridgeIcons(
    @DrawableRes val fridge: Int = R.drawable.baseline_kitchen_24,
    @DrawableRes val more: Int = R.drawable.baseline_more_vert_24,
    @DrawableRes val tune: Int = R.drawable.baseline_tune_24
)

data class FridgeActions(
    @StringRes val mode: Int = R.string.mode
)
@Composable
fun FridgeCard(
    name : String,
    fridgeToRend: FridgeIcons = FridgeIcons(),
    modes: Array<String> = stringArrayResource(id = R.array.fridge_modes)
){
val dialogOpen = remember {
    mutableStateOf(false)
}
val modesOpen = remember {
    mutableStateOf(false)
}

val mode = remember {
    mutableStateOf( " ")
}


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
                text = name,
                fontSize = 8.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }
        Row {
                Icon(
                    painter = painterResource(fridgeToRend.fridge),
                    contentDescription = stringResource(R.string.fridge),
                    modifier = Modifier.size(45.dp)
                )

        }
        Row {
            Text(
                text = "MODE: ${mode.value}",
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
                Icon(painter = painterResource(id = fridgeToRend.tune),
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
                    painter = painterResource(fridgeToRend.more),
                    contentDescription = null
                )
            }
        }
}

if (dialogOpen.value) {
    Dialog(
        onDismissRequest = { dialogOpen.value = false }
    ) {
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
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
                    TextButton(
                        onClick = {
                            dialogOpen.value = false
                        },
                    ) {
                        Text(stringResource(id = R.string.confirmation))
                    }
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
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Text(text = stringResource(id = R.string.modeMSG))
                Row() {
                    LazyRow(){
                        items(items = modes){
                            item ->
                            TextButton(
                                onClick = { mode.value = item },
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
    FridgeCard(name = "Hello World")
}*/
