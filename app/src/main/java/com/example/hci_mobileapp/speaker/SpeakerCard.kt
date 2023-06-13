package com.example.hci_mobileapp.speaker

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

@Composable
fun SpeakerCard(
    speakerViewModel: SpeakerViewModel = viewModel(),
    name: String
    ){
    val speakerUiState = speakerViewModel.uiState.collectAsState()

    val genDialog = remember { mutableStateOf(false) }

    val openDialog = remember { mutableStateOf(false) }

    speakerViewModel.nameSet(name)
    speakerViewModel.turnOnOff()

    val genres = stringArrayResource(speakerUiState.value.genres)

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
                    text = speakerUiState.value.name,
                    fontSize = 8.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
            Row {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(speakerUiState.value.icons.speaker),
                        contentDescription = stringResource(R.string.speaker),
                        modifier = Modifier.size(45.dp)
                    )
                }
            }
            Row {
                Text(
                    text = "Playing " + speakerUiState.value.currGen,
                    fontSize = 9.sp,
                    modifier = Modifier
                        .padding(horizontal = 7.dp)

                )
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

                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(speakerUiState.value.icons.prev),
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick ={ }
                ) {
                    Icon(
                        painter = painterResource(speakerUiState.value.icons.play),
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(speakerUiState.value.icons.next),
                        contentDescription = null
                    )
                }
                IconButton(onClick = { openDialog.value = true }) {
                    Icon(
                        painter = painterResource(speakerUiState.value.icons.more),
                        contentDescription = null
                    )
                }
            }
        }
    }

    if (openDialog.value) {
        Dialog(
            onDismissRequest = { openDialog.value = false }
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
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(onClick = { }
                        ) {
                            Icon(
                                painter = painterResource(speakerUiState.value.icons.playList),
                                contentDescription = null
                            )
                            Text(text = stringResource(speakerUiState.value.actions.plist))
                        }
                        TextButton(onClick = { genDialog.value = true }
                        ) {
                            Icon(
                                painter = painterResource(speakerUiState.value.icons.genres),
                                contentDescription = null
                            )
                            Text(text = stringResource(speakerUiState.value.actions.gen))
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            },
                        ) {
                            Text(stringResource(id = R.string.confirmation))
                        }
                    }
                }
            }
        }
    }

    if (genDialog.value) {
        Dialog(
            onDismissRequest = { genDialog.value = false }
        ) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Row() {
                    LazyRow() {
                        items(items = genres) { item ->
                            TextButton(
                                onClick = { speakerViewModel.genreSet(item) },
                                modifier = Modifier.padding(start = 1.dp, end = 1.dp)
                            ) {
                                Text(item)
                            }
                        }
                    }
                }
                Row(verticalAlignment = Alignment.Bottom) {
                    TextButton(
                        onClick = {
                            genDialog.value = false
                        },
                    ) {
                        Text(stringResource(id = R.string.confirmation))
                    }
                }
            }
        }
    }
}




@Preview
@Composable
fun CardPrev(){
    SpeakerCard(name = "Hello World")
}
