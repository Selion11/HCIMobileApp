package com.example.hci_mobileapp.faucet

import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.hci_mobileapp.R


data class FaucetIcons(
    @DrawableRes val faucetOn: Int = R.drawable.baseline_water_drop_24,
    @DrawableRes val faucetOff: Int = R.drawable.outline_water_drop_24,
    @DrawableRes val disp: Int = R.drawable.dispense
)

data class FaucetActions(
    @StringRes val dispense: Int = R.string.dispense,
    @StringRes val msg: Int = R.string.txt_field_disp,
    @StringRes val supp: Int = R.string.suppFaucet
)

data class FaucetUiState (
    val name: String = " ",
    val icons: FaucetIcons = FaucetIcons(),
    val actions: FaucetActions = FaucetActions(),
    @ArrayRes val units: Int = R.array.units,
    val dispenseUnits: String = " ",
    val state: Int = R.string.Off
)
