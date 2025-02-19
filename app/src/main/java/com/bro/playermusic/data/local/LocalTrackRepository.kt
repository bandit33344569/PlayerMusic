package com.bro.playermusic.data.local

import com.bro.playermusic.data.entities.LocalTrack
import com.bro.playermusic.domain.model.Track
import com.bro.playermusic.domain.repo.LocalAudioRepository

class LocalTrackRepository(private val localDataSource: LocalAudioDataSource) :
    LocalAudioRepository {
    override suspend fun getAudioFiles(): List<Track> {
        return localDataSource.getLocalAudioTracks().map { toAudioFile(it) }
    }

    override suspend fun searchTracks(query: String): List<Track> {
        return emptyList()
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