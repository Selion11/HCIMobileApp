package com.example.hci_mobileapp.speaker

import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.hci_mobileapp.R

data class SpeakerIcons(
    @DrawableRes val speaker: Int = R.drawable.baseline_speaker_24,
    @DrawableRes val play: Int = R.drawable.baseline_play_arrow_24,
    @DrawableRes val next: Int = R.drawable.baseline_skip_next_24,
    @DrawableRes val prev: Int = R.drawable.baseline_skip_previous_24,
    @DrawableRes val more: Int = R.drawable.baseline_more_vert_24,
    @DrawableRes val playList: Int = R.drawable.baseline_playlist_play_24,
    @DrawableRes val genres: Int = R.drawable.baseline_music_note_24

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
    val currGen: String = " ",
    val state: String = " "
)