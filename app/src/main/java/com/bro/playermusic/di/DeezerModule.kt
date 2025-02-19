package com.bro.playermusic.di

import com.bro.playermusic.data.network.DeezerApiService
import com.bro.playermusic.data.network.DeezerTrackRepository
import com.bro.playermusic.domain.repo.ApiAudioRepository
import com.bro.playermusic.domain.usecase.GetChartTracksUseCase
import com.bro.playermusic.domain.usecase.SearchTracksUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeezerModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val BASE_URL = "https://api.deezer.com/"
        val contentType = "application/json".toMediaType()
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideDeezerApiService(retrofit: Retrofit): DeezerApiService {
        return retrofit.create(DeezerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDeezerTrackRepository(apiService: DeezerApiService): ApiAudioRepository {
        return DeezerTrackRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideChartTracksUseCase(deezerTrackRepository: DeezerTrackRepository): GetChartTracksUseCase {
        return GetChartTracksUseCase(deezerTrackRepository)
    }

    @Provides
    @Singleton
    fun provideSearchTrackUseCase(deezerTrackRepository: DeezerTrackRepository) : SearchTracksUseCase {
        return SearchTracksUseCase(deezerTrackRepository)
    }
}