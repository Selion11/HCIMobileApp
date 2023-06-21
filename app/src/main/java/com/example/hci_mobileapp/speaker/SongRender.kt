package com.example.hci_mobileapp.speaker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hci_mobileapp.data.network.model.Song


@Composable
fun SongRender(song: Song) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 10.dp),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(width = 2.dp, color = Color.Black),

    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 4.dp,
                    vertical = 6.dp
                )
        ) {
            Row {
                Text(
                    text = song.title.toString(),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }
            Row {
                Icon(
                    painter = painterResource(SpeakerUiState().icons.song),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Text(
                    text = song.artist.toString(),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(
                    start = 150.dp,
                    top = 6.dp,
                    end = 9.dp
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Row {
                Text(
                    text = "Duration: " + song.duration.toString(),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 15.dp),
            ) {
                Text(
                    text = "Album : " + song.album.toString(),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

        }
    }

}