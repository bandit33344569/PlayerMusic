package com.bro.playermusic.domain.usecase

import com.bro.playermusic.domain.model.Track
import com.bro.playermusic.domain.repo.LocalAudioRepository

class SubscribeLocalTrackUseCase(private val repository: LocalAudioRepository) {

    suspend operator fun invoke(): List<Track> {
        return repository.getAudioFiles()
    }
}