package com.example.hci_mobileapp.devicesView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.speaker.SpeakerCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


/*@OptIn(ExperimentalMaterial3Api::class)
private val devices = listOf(
   "Hello","Goodbye","I am Narnia"
)*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun renderDevices(
    modifier: Modifier = Modifier,
    viewModel: DevicesViewModel
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
            val devices = devicesViewUiState.value.devices?.devicesList
            LazyColumn(){
                items(items = devices){ item ->
                    SpeakerCard(name = item.name?: "")
                }
            }
        }
    }
}


@Composable
fun DevicesView(devicesViewModel: DevicesViewModel = viewModel()){
}

/*
@Preview
@Composable
fun devPrev(){
    renderDevices()
}*/
