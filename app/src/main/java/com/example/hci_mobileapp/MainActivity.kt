package com.example.hci_mobileapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
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
fun topNavigation(){
    var select = remember{ mutableStateOf(1) }
    Row(
        horizontalArrangement = Arrangement
            .spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        TextButton(onClick = {select.value = 1 }) {
            Icon(painter = painterResource(R.drawable.baseline_home_24),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
            Text(text = "Home", color = Color.Black)
        }
        TextButton(onClick = { select.value = 2 }) {
            Icon(painter = painterResource(R.drawable.baseline_devices_24),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 5.dp))
            Text(text = "All devices", color = Color.Black)
        }
    }
    TextButton(onClick = { select.value = 3 }) {
        Icon(painter = painterResource(R.drawable.baseline_access_time_24),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .padding(horizontal = 5.dp))
        Text(text = "Routines", color = Color.Black)
    }
}
@Composable
fun dummyView(){
    Row() {
        topNavigation()
    }
    Row(modifier = Modifier.padding(top= 50.dp, start = 10.dp)) {
        renderDevices()
    }
}
@Preview
@Composable
fun topPrev(){
    topNavigation()
}

