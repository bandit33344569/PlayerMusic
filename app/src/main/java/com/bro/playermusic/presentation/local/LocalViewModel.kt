package com.bro.playermusic.presentation.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bro.playermusic.domain.model.Track
import com.bro.playermusic.domain.usecase.SubscribeLocalTrackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModel @Inject constructor(
    private val subscribeLocalTrackUseCase: SubscribeLocalTrackUseCase
) : ViewModel() {


    private var originalTracks: List<Track> = emptyList()

    private val _tracks: MutableStateFlow<List<Track>> = MutableStateFlow(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    init {
        viewModelScope.launch {
            originalTracks = subscribeLocalTrackUseCase.invoke()
            _tracks.value = originalTracks
        }
    }

    fun getFilteredTracks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val filteredTracks = if (query.isBlank()) {
                originalTracks
            } else {
                originalTracks.filter { track ->
                    track.title.contains(query, ignoreCase = true) ||
                            track.author.contains(query, ignoreCase = true)
                }
            }
            _tracks.value = filteredTracks
        }
    }
}
