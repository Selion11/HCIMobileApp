package com.example.hci_mobileapp.ac

import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.hci_mobileapp.R


data class AcIcons(
    @DrawableRes val on: Int = R.drawable.airon,
    @DrawableRes val off: Int = R.drawable.airoff,
    @DrawableRes val horSwing: Int = R.drawable.horizontalswing,
    @DrawableRes val vertSwing: Int = R.drawable.verticalswing,
    @DrawableRes val fanSpeed: Int = R.drawable.fanspeed,
    @DrawableRes val mode: Int = R.drawable.baseline_tune_24
)


data class AcActions(
    @StringRes val vertSwing: Int = R.string.vertical_swing,
    @StringRes val horSwing: Int = R.string.horizontal_swing,
    @StringRes val fanSpeed: Int = R.string.fan_speed,
    @StringRes val mode: Int = R.string.mode
)

data class AcArrays(
    @ArrayRes val AcModes: Int = R.array.ac_modes,
    @ArrayRes val vertValues: Int = R.array.vertical_values,
    @ArrayRes val horValues: Int = R.array.horizontal_values,
    @ArrayRes val fanSpeed: Int = R.array.fanSpeeds
)

data class AcUiState(
    val name: String = " ",
    val id: String = " ",
    val icons: AcIcons = AcIcons(),
    val actions: AcActions = AcActions(),
    val arrays: AcArrays = AcArrays(),
    val mode: String = " ",
    val vertValue: String = " ",
    val horVal: String = " ",
    val speed: String = " ",
    val temperature: Int = 24,
    val state: Int = R.string.Off
)