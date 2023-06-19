package com.example.hci_mobileapp.devicesView

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.ac.AcCard
import com.example.hci_mobileapp.data.network.model.ApiDevice
import com.example.hci_mobileapp.faucet.FaucetCard
import com.example.hci_mobileapp.fridge.FridgeCard
import com.example.hci_mobileapp.lamp.LampCard
import com.example.hci_mobileapp.speaker.SpeakerCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
private val devi = listOf(
   "Hello","Goodbye","I am Narnia"
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun renderDevices(
    //modifier: Modifier = Modifier,
    viewModel: DevicesViewModel = viewModel()
){
    val devicesViewUiState = viewModel.uiState.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = devicesViewUiState.value.isLoading)
        , onRefresh = { viewModel.fetchAllDevices() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
        ){
            Button(onClick = { viewModel.fetchAllDevices() }) {
                Text(text = "FETCH")
            }
            val devices = devicesViewUiState.value.devices?.devicesList
            if(devices != null) {
                for (device in devices) {
                    when(device.type?.id){
                        "c89b94e8581855bc" -> SpeakerCard(data = device)
                        "li6cbv5sdlatti0j" -> AcCard(data = device)
                        "rnizejqr2di0okho" -> FridgeCard(data = device)
                        "dbrlsh7o5sn8ur4i" -> FaucetCard(data = device)
                        "go46xmbqeomjrsjr" -> LampCard(data = device)
                    }
                }
            }
        }     
    } 
}


/*
@Preview
@Composable
fun devPrev(){
    renderDevices()
}*/
