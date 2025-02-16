package com.bro.playermusic.data.local

import com.bro.playermusic.data.LocalTrack
import com.bro.playermusic.domain.model.Track
import com.bro.playermusic.domain.repo.AudioRepository

class LocalTrackRepository(private val localDataSource: LocalAudioDataSource) : AudioRepository {
    override suspend fun getAudioFiles(): List<Track> {
        return localDataSource.getLocalAudioTracks().map { toAudioFile(it) }
    }
}

fun toAudioFile(localLocalTrack: LocalTrack): Track {
    return with(localLocalTrack) {
        Track(
            id = this.id,
            title = this.title,
            author = this.author,
            pathUri = this.uri,
            imageUri = this.albumArtUri
        )
    }
}