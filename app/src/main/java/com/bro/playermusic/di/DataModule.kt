package com.bro.playermusic.di

import android.content.Context
import com.bro.playermusic.data.local.LocalAudioDataSource
import com.bro.playermusic.data.local.LocalTrackRepository
import com.bro.playermusic.domain.usecase.SubscribeLocalTrackUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAudioDataSource(@ApplicationContext context: Context): LocalAudioDataSource {
        return LocalAudioDataSource(context)
    }

    @Provides
    @Singleton
    fun provideAudioLocalRepository(localDataSource: LocalAudioDataSource): LocalTrackRepository {
        return LocalTrackRepository(localDataSource)
    }

    @Provides
    @Singleton
    fun provideGetLocalTrackUseCase(audioRepository: LocalTrackRepository): SubscribeLocalTrackUseCase {
        return SubscribeLocalTrackUseCase(audioRepository)
    }
}