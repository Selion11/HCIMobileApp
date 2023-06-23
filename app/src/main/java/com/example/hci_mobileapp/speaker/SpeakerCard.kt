package com.example.hci_mobileapp.speaker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialogDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.R


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerCard(
    speakerViewModel: SpeakerViewModel = viewModel(),
    ) {
    val speakerUiState = speakerViewModel.uiState.collectAsState()

    val genDialog = remember { mutableStateOf(false) }

    val openDialog = remember { mutableStateOf(false) }

    val playlistDialog = remember { mutableStateOf(false) }

    val intensityDialog = remember { mutableStateOf(false) }

    val genres = speakerUiState.value.genres.let { stringArrayResource(it) }

    val songs = speakerUiState.value.playList

    fun openAndDoPlaylist() {
        playlistDialog.value = true;
        speakerViewModel.getPlaylist()
    }

    fun genreAndClose(gen :String){
        speakerViewModel.genreSet(gen)
        genDialog.value = false
    }

    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(width = 2.dp, color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(95.dp),
        onClick = { openDialog.value = true }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 3.dp,
                    vertical = 4.dp
                ),
        ) {
            Row {
                speakerUiState.value.name?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                    )
                }
            }
            Row {
                speakerUiState.value.icons.let { painterResource(it.speaker) }.let {
                    Icon(
                        painter = it,
                        contentDescription = stringResource(R.string.speaker),
                        modifier = Modifier.size(45.dp)
                    )
                }
            }
            Row {
                Text(
                    text = "Playing " + speakerUiState.value.currGen,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)

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
                    .fillMaxWidth()
                    .padding(top = 18.dp)
                    .height(55.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                IconButton(onClick = { speakerViewModel.prevSong() }) {
                    Icon(
                        painter = painterResource(speakerUiState.value.icons.prev),
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = { speakerViewModel.playPause() }
                ) {
                    Icon(
                        painter = painterResource(speakerViewModel.iconSelectPlay()),
                        contentDescription = null,
                    )
                }
                IconButton(
                    onClick = { speakerViewModel.stop() }
                ) {
                    Icon(
                        painter = painterResource(speakerUiState.value.icons.stop),
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { speakerViewModel.nextSong() }) {
                    Icon(
                        painter = painterResource(speakerUiState.value.icons.next),
                        contentDescription = null
                    )
                }
            }
        }
    }

    if (openDialog.value) {
        Dialog(
            onDismissRequest = { openDialog.value = false },
        ) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(300.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .scrollable(
                            state = rememberScrollState(),
                            orientation = Orientation.Vertical
                        ),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row() {
                        TextButton(onClick = { openAndDoPlaylist() }
                        ) {
                            Icon(
                                painter = painterResource(speakerUiState.value.icons.playList),
                                contentDescription = null
                            )
                            Text(text = stringResource(speakerUiState.value.actions.plist))
                        }
                    }
                    Row() {
                        TextButton(onClick = { genDialog.value = true }
                        ) {
                            Icon(
                                painter = painterResource(speakerUiState.value.icons.genres),
                                contentDescription = null
                            )
                            Text(text = stringResource(speakerUiState.value.actions.gen))
                        }
                    }
                    Row() {
                        TextButton(
                            onClick = { intensityDialog.value = true }
                        ) {
                            Icon(
                                painter = painterResource(speakerUiState.value.icons.vol),
                                contentDescription = null
                            )
                            Text(text = stringResource(speakerUiState.value.actions.vol))
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
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
                                onClick = { genreAndClose(item) },
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

    if (intensityDialog.value) {
        Dialog(onDismissRequest = { intensityDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(100.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Text(
                    text = speakerUiState.value.volume.toString(),
                    modifier = Modifier
                        .padding(start = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(top = 15.dp)
                ) {
                    IconButton(onClick = {
                        speakerViewModel.setVolume(speakerUiState.value.volume?.minus(1) ?: 0)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = null
                        )
                    }
                    speakerUiState.value.volume?.let {
                        Slider(
                            value = it.toFloat(),
                            onValueChange = { speakerViewModel.setVolume(it.toInt()) },
                            valueRange = 0f..10f,
                            modifier = Modifier.width(240.dp)
                        )
                    }
                    IconButton(onClick = {
                        speakerViewModel.setVolume(speakerUiState.value.volume?.plus(1) ?: 0)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_add_24),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
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

    if (playlistDialog.value) {
        Dialog(
            onDismissRequest = { playlistDialog.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Row{
                if (songs != null) {
                    LazyColumn() {
                        items(items = songs) { item ->
                            SongRender(song = item)
                        }
                    }
                }
            }
        }
    }
}





/*@Preview
@Composable
fun CardPrev(){
    songRender();
}*/

