package com.bro.playermusic.domain.repo

import com.bro.playermusic.domain.model.Track

interface AudioRepository {
    suspend fun getAudioFiles(): List<Track>
}