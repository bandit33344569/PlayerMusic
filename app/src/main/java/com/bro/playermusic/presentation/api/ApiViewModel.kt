package com.bro.playermusic.presentation.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bro.playermusic.domain.model.Track
import com.bro.playermusic.domain.usecase.GetChartTracksUseCase
import com.bro.playermusic.domain.usecase.SearchTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val getChartTracksUseCase: GetChartTracksUseCase,
    private val searchTracksUseCase: SearchTracksUseCase

) : ViewModel() {

    private val _tracks: MutableStateFlow<List<Track>> = MutableStateFlow(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    init {
        getChartTracks()
    }

    fun getChartTracks() {
        viewModelScope.launch {
            _tracks.value = getChartTracksUseCase.invoke()
        }
    }

    fun searchTracks(query: String) {
        viewModelScope.launch {
            _tracks.value = searchTracksUseCase.invoke(query)
        }
    }
}