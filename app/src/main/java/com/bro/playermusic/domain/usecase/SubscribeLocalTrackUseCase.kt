package com.bro.playermusic.domain.usecase

import com.bro.playermusic.domain.model.Track
import com.bro.playermusic.domain.repo.AudioRepository

class SubscribeLocalTrackUseCase(private val repository: AudioRepository) {

    suspend operator fun invoke(): List<Track> {
        return repository.getAudioFiles()
    }
}