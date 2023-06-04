package com.example.hci_mobileapp

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.Composable
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
import javax.sql.RowSet

data class speakerIcons(
    @DrawableRes val speaker: Int,
    @DrawableRes val play: Int,
    @DrawableRes val next: Int,
    @DrawableRes val prev: Int,
    @DrawableRes val more: Int
)
val speakerRenderer = speakerIcons(
                                            speaker = R.drawable.baseline_speaker_24,
                                            prev = R.drawable.baseline_skip_previous_24,
                                            play =  R.drawable.baseline_play_arrow_24,
                                            next = R.drawable.baseline_skip_next_24,
                                            more = R.drawable.baseline_more_vert_24
                                    )
@ExperimentalMaterial3Api
@Composable
fun SpeakerCard(
    name : String,
    modifier: Modifier = Modifier,
   speakertoRend: speakerIcons = speakerRenderer){
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        border = BorderStroke(width = 2.dp, color = Color.Black),
        onClick = {}
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

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CardPrev(){
    SpeakerCard(name = "Speaker")
}
