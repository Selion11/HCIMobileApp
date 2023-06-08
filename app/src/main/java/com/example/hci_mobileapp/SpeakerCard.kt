package com.example.hci_mobileapp

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

data class SpeakerIcons(
    @DrawableRes val speaker: Int = R.drawable.baseline_speaker_24,
    @DrawableRes val play: Int = R.drawable.baseline_play_arrow_24,
    @DrawableRes val next: Int = R.drawable.baseline_skip_next_24,
    @DrawableRes val prev: Int = R.drawable.baseline_skip_previous_24 ,
    @DrawableRes val more: Int = R.drawable.baseline_more_vert_24
)

data class SpeakerActions(
    @StringRes val plist: Int = R.string.playlist,
    @StringRes val gen: Int = R.string.genre
)

@ExperimentalMaterial3Api
@Composable
fun SpeakerCard(
    name : String,
    modifier: Modifier = Modifier,
    speakerToRend: SpeakerIcons = SpeakerIcons(),
    actions: SpeakerActions = SpeakerActions(),
    genres: Array<String> = stringArrayResource(id = R.array.genres)
) {
    val dialogOpen = remember {
        mutableStateOf(false)
    }
    val genresOpen = remember {
        mutableStateOf(false)
    }

    val gen = remember {
        mutableStateOf(" ")
    }

    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
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
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(speakerToRend.speaker),
                        contentDescription = stringResource(R.string.speaker),
                        modifier = Modifier.size(45.dp)
                    )
                }
            }
            Row {
                Text(
                    text = "Playing ${gen.value}",
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

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(speakerToRend.prev),
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(speakerToRend.play),
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(speakerToRend.next),
                        contentDescription = null
                    )
                }
                IconButton(onClick = { dialogOpen.value = true }) {
                    Icon(
                        painter = painterResource(speakerToRend.more),
                        contentDescription = null
                    )
                }
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
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_playlist_play_24),
                                contentDescription = null
                            )
                            Text(text = stringResource(actions.plist))
                        }
                        TextButton(onClick = { genresOpen.value = true }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_music_note_24),
                                contentDescription = null
                            )
                            Text(text = stringResource(actions.gen))
                        }
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
    }
    if (genresOpen.value) {
        Dialog(
            onDismissRequest = { genresOpen.value = false }
        ) {
            Surface(
                modifier = Modifier
                    .width(365.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Row() {
                    LazyRow(){
                        items(items = genres){
                                item ->
                            TextButton(
                                onClick = { gen.value = item },
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
                            genresOpen.value = false
                        },
                    ) {
                        Text(stringResource(id = R.string.confirmation))
                    }
                }
            }
        }
    }
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CardPrev(){
    SpeakerCard(name = "Hello World")
}*/

