package com.example.hci_mobileapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobileapp.ui.theme.HCIMobileAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HCIMobileAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    dummyView()
                }
            }
        }
    }
}

@Composable
fun TopNavigation(){
    val select = remember{ mutableStateOf(1) }
        Column() {
            TextButton(
                onClick = {select.value = 1 },
                border = BorderStroke(width = 2.dp, color = Color.Black),
                modifier = Modifier
                    .padding(start = 10.dp),
                shape = MaterialTheme.shapes.medium
                ) {
                Icon(painter = painterResource(R.drawable.baseline_home_24),
                    contentDescription = null,
                    tint = Color.Black,
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start= 215.dp)
        ) {
            TextButton(
                onClick = { select.value = 2 },
                border = BorderStroke(width = 2.dp, color = Color.Black),
                shape = MaterialTheme.shapes.medium
               ) {
                Icon(painter = painterResource(R.drawable.baseline_devices_24),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 5.dp))
                }
            }
        }

@Composable
fun dummyView(){
    Row(verticalAlignment = Alignment.Top) {
        Text(text = "Title Placeholder", textAlign = TextAlign.Center)
    }
    Row(modifier = Modifier.padding(top= 50.dp), horizontalArrangement = Arrangement.Center) {
        renderDevices()
    }
    Row(verticalAlignment = Alignment.Bottom) {
        TopNavigation()
    }
}
@Preview
@Composable
fun topPrev(){
    dummyView()
}

