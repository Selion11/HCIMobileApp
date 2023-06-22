package com.example.hci_mobileapp.devicesView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.ac.AcCard
import com.example.hci_mobileapp.ac.AcViewModel
import com.example.hci_mobileapp.faucet.FacuetViewModel
import com.example.hci_mobileapp.faucet.FaucetCard
import com.example.hci_mobileapp.fridge.FridgeCard
import com.example.hci_mobileapp.fridge.FridgeViewModel
import com.example.hci_mobileapp.lamp.LampCard
import com.example.hci_mobileapp.lamp.LampViewModel
import com.example.hci_mobileapp.notification.ShowNotificationReceiver
import com.example.hci_mobileapp.speaker.SongRender
import com.example.hci_mobileapp.speaker.SpeakerCard
import com.example.hci_mobileapp.speaker.SpeakerViewModel


@Composable
fun renderDevices(
    //modifier: Modifier = Modifier,
    viewModel: DevicesViewModel = viewModel(),
) {
    val devicesViewUiState = viewModel.uiState.collectAsState()
    val devs = devicesViewUiState.value.devices
   Column(
       verticalArrangement = Arrangement.spacedBy(8.dp),
       modifier = Modifier
           .fillMaxWidth()
           .padding(horizontal = 6.dp)
   ) {
        Button(
            onClick = { viewModel.fetchAllDevices() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "FETCH")
        }
        if (devs != null) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = devs) { item ->
                    when (item.type?.id) {
                        "c89b94e8581855bc" -> SpeakerCard(speakerViewModel = SpeakerViewModel(item))
                        "li6cbv5sdlatti0j" -> AcCard(acViewModel = AcViewModel(item))
                        "rnizejqr2di0okho" -> FridgeCard(fridgeViewModel = FridgeViewModel(item))
                        "dbrlsh7o5sn8ur4i" -> FaucetCard(faucetViewModel = FacuetViewModel(item))
                        "go46xmbqeomjrsjr" -> LampCard(lampViewModel = LampViewModel(item))
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
