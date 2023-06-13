package com.example.hci_mobileapp.fridge

import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.hci_mobileapp.R


data class FridgeIcons(
    @DrawableRes val fridge: Int = R.drawable.baseline_kitchen_24,
    @DrawableRes val more: Int = R.drawable.baseline_more_vert_24,
    @DrawableRes val tune: Int = R.drawable.baseline_tune_24
)

data class FridgeActions(
    @StringRes val mode: Int = R.string.mode
)

data class FridgeUiState (
    val icons: FridgeIcons = FridgeIcons(),
    val actions: FridgeActions = FridgeActions(),
    val name: String = " ",
    val freezerTemp: Int = -2,
    val fridgeTemp: Int = 4,
    @ArrayRes val modes: Int = R.array.fridge_modes,
    val curMode: String = " "
)