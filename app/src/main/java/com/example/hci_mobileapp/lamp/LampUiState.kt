package com.example.hci_mobileapp.lamp

import androidx.annotation.ColorLong
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.hci_mobileapp.R

data class LampIcons(
    @DrawableRes val lampOn: Int = R.drawable.baseline_lightbulb_24,
    @DrawableRes val lampOff: Int = R.drawable.outline_lightbulb_24,
    @DrawableRes val intense: Int = R.drawable.baseline_light_mode_24,
    @DrawableRes val colorPicker: Int = R.drawable.baseline_color_lens_24
)

data class LampActions(
    @StringRes val inten: Int = R.string.intensity,
    @StringRes val colChange: Int = R.string.change_color
)

data class LampUiState(
    val name: String = " ",
    val icons: LampIcons = LampIcons(),
    val actions: LampActions = LampActions(),
    val state: Int = 0,
    @ColorLong val col: Long = 0x000000,
    val intensity: Float = 5f
)