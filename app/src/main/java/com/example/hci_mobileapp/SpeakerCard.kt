package com.example.hci_mobileapp

import android.graphics.Paint.Align
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import javax.sql.RowSet

data class speakerIcons(
    @DrawableRes val speaker: Int = R.drawable.baseline_speaker_24,
    @DrawableRes val play: Int = R.drawable.baseline_play_arrow_24,
    @DrawableRes val next: Int = R.drawable.baseline_skip_next_24,
    @DrawableRes val prev: Int = R.drawable.baseline_skip_previous_24 ,
    @DrawableRes val more: Int = R.drawable.baseline_more_vert_24
)

data class speakerStringActions(
    @StringRes val playlist: Int = R.string.playlist,
    @StringRes val genre: Int = R.string.genre,
)

data class speakerGenres(
    @StringRes val clasical: Int = R.string.classic,
    @StringRes val dance: Int = R.string.dance,
    @StringRes val country: Int = R.string.country,
    @StringRes val pop: Int = R.string.pop,
    @StringRes val rock: Int = R.string.rock,
)
@ExperimentalMaterial3Api
@Composable
fun SpeakerCard(
    name : String,
    modifier: Modifier = Modifier,
   speakertoRend: speakerIcons = speakerIcons(),
    actions: speakerStringActions = speakerStringActions(),
    genres: speakerGenres = speakerGenres()
){
    val dialogOpen = remember {
        mutableStateOf(false)
    }
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        border = BorderStroke(width = 2.dp, color = Color.Black),
        onClick = {dialogOpen.value = true}
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 3.dp,
                    vertical = 4.dp)
        ) {
            Row{
                Text(
                    text = name,
                    fontSize = 8.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
            Row {
                IconButton(onClick = {}){
                    Icon(painter = painterResource(speakertoRend.speaker),
                        contentDescription = stringResource(R.string.speaker),
                        modifier = Modifier.size(45.dp))
                }
            }
            Row {
                Text(
                    text = "Playing Hello World!",
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
                    Icon(painter = painterResource(speakertoRend.prev),
                        contentDescription = null)
                }
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(speakertoRend.play),
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(speakertoRend.next),
                        contentDescription = null
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(speakertoRend.more),
                        contentDescription = null
                    )
                }
            }
        }
    }
    if (dialogOpen.value){
        Dialog(onDismissRequest = { dialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .width(350.dp)
                    .height(150.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp),
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                       TextButton(onClick = { /*TODO*/ }
                       ) {
                           Icon(painter = painterResource(id = R.drawable.baseline_playlist_play_24),
                               contentDescription = null)
                           Text(text = stringResource(actions.playlist))
                       }
                        TextButton(onClick = { /*TODO*/ }
                        ) {
                            Icon(painter = painterResource(id = R.drawable.baseline_music_note_24),
                                contentDescription = null)
                            Text(text = stringResource(actions.genre))
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(verticalAlignment = Alignment.Bottom){
                        TextButton(
                                onClick = {
                                    dialogOpen.value = false
                                },
                            ) {
                                Text("Confirm")
                            }
                        }
                    }

            }
        }
        }
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CardPrev(){
    speakerDialog()
}
*/
