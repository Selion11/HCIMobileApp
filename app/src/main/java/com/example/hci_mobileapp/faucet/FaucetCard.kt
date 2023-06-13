package com.example.hci_mobileapp.faucet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobileapp.R

@Composable
fun FaucetCard(
    faucetViewModel: FacuetViewModel = viewModel(),
    name: String
){
    val faucetUiState = faucetViewModel.uiState.collectAsState()

    faucetViewModel.nameSet(name)

    val dispenseDialog = remember { mutableStateOf(false) }


    Surface(
        shape = MaterialTheme.shapes.small,
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
                    text = faucetUiState.value.name,
                    fontSize = 8.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
            Row {
                IconButton(onClick = {faucetViewModel.turnOnOff() }) {
                    Icon(
                        painter = painterResource(faucetViewModel.iconSelection()),
                        contentDescription = stringResource(R.string.lamp),
                        modifier = Modifier.size(45.dp),

                        )
                }
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

                IconButton(onClick = {/* Open dialog and picker to select value
                      send that value to color set*/  },
                    enabled = faucetUiState.value.state == R.string.On
                ) {
                    Icon(
                        painter = painterResource(faucetUiState.value.icons.disp),
                        contentDescription = null
                    )
                }
            }
        }
    }
}