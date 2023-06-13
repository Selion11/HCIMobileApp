package com.example.hci_mobileapp

import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource

data class SpeakerIcons(
    @DrawableRes val speaker: Int = R.drawable.baseline_speaker_24,
    @DrawableRes val play: Int = R.drawable.baseline_play_arrow_24,
    @DrawableRes val next: Int = R.drawable.baseline_skip_next_24,
    @DrawableRes val prev: Int = R.drawable.baseline_skip_previous_24,
    @DrawableRes val more: Int = R.drawable.baseline_more_vert_24,
    @DrawableRes val playList: Int = R.drawable.baseline_playlist_play_24,
    @DrawableRes val genres: Int =  R.drawable.baseline_music_note_24

)

data class SpeakerActions(
    @StringRes val plist: Int = R.string.playlist,
    @StringRes val gen: Int = R.string.genre
)

data class SpeakerUiState (
    val icons: SpeakerIcons = SpeakerIcons(),
    val actions: SpeakerActions = SpeakerActions(),
    val name : String = " ",
    @ArrayRes val genres: Int = R.array.genres,
    val currGen: String = " "
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SpeakerUiState

        if (icons != other.icons) return false
        if (actions != other.actions) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = icons.hashCode()
        result = 31 * result + actions.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}