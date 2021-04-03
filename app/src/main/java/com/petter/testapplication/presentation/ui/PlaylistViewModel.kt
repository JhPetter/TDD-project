package com.petter.testapplication.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.repository.PlayListRepository

class PlaylistViewModel(private val playListRepository: PlayListRepository) : ViewModel() {
    val playlistLiveData: LiveData<Result<List<Playlist>>> =
        liveData { emitSource(playListRepository.fetchPlaylist().asLiveData()) }

}