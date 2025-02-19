package com.bro.playermusic.data.network

import android.net.Uri
import com.bro.playermusic.data.entities.DeezerTrack
import com.bro.playermusic.domain.model.Track
import com.bro.playermusic.domain.repo.ApiAudioRepository
import javax.inject.Inject

class DeezerTrackRepository @Inject constructor(private val apiService: DeezerApiService) :
    ApiAudioRepository {


    override suspend fun getChartTracks(): List<Track> {
        val response = apiService.getChart()
        return response.tracks.data.map { it.toTrack() }
    }

    override suspend fun searchTracks(query: String): List<Track> {
        val response = apiService.searchTracks(query)
        return response.data.map { it.toTrack() }
    }

    private fun DeezerTrack.toTrack(): Track {
        return Track(
            id = this.id,
            title = this.title,
            author = this.artist.name,
            pathUri = Uri.parse(this.preview),
            imageUri = Uri.parse(this.album.coverMedium)
        )
    }
}