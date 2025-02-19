package com.bro.playermusic.domain.usecase

import com.bro.playermusic.data.network.DeezerTrackRepository
import com.bro.playermusic.domain.model.Track

class SearchTracksUseCase(private val repository: DeezerTrackRepository) {

    suspend operator fun invoke(query: String): List<Track> {
        return repository.searchTracks(query)
    }
}