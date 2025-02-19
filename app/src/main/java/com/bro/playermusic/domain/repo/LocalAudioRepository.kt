package com.bro.playermusic.domain.repo

import com.bro.playermusic.domain.model.Track

interface LocalAudioRepository {
    suspend fun getAudioFiles(): List<Track>
    suspend fun searchTracks(query: String): List<Track>
}
