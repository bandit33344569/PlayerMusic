package com.bro.playermusic.data.entities

import android.net.Uri

data class LocalTrack(
    val id: Long,
    val title: String,
    val author: String,
    val uri: Uri,
    val albumArtUri: Uri
)
