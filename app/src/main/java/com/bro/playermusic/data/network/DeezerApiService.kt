package com.bro.playermusic.data.network

import com.bro.playermusic.data.entities.DeezerTrack
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {

    @GET("chart")
    suspend fun getChart(): DeezerChartResponse

    @GET("search")
    suspend fun searchTracks(@Query("q") query: String): DeezerSearchResponse

    @GET("track/{id}")
    suspend fun getTrack(@Path("id") id: Int): DeezerTrack
}

@Serializable
data class DeezerChartResponse(val tracks: DeezerData<DeezerTrack>)
@Serializable
data class DeezerSearchResponse(val data: List<DeezerTrack>)
@Serializable
data class DeezerData<T>(val data: List<T>)