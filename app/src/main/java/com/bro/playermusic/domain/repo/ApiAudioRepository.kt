package com.bro.playermusic.domain.repo

import com.bro.playermusic.domain.model.Track

interface ApiAudioRepository {
    suspend fun getChartTracks(): List<Track>
    suspend fun searchTracks(query: String): List<Track>
}