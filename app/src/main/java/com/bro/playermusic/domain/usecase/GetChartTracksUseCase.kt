package com.bro.playermusic.domain.usecase

import com.bro.playermusic.domain.model.Track
import com.bro.playermusic.domain.repo.ApiAudioRepository


class GetChartTracksUseCase(private val repository: ApiAudioRepository) {
    suspend operator fun invoke(): List<Track> {
        return repository.getChartTracks()
    }
}