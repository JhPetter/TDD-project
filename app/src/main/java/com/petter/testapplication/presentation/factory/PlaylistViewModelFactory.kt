package com.petter.testapplication.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petter.testapplication.presentation.ui.PlaylistViewModel
import com.petter.testapplication.repository.PlayListRepository

class PlaylistViewModelFactory(private val playListRepository: PlayListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaylistViewModel(playListRepository) as T
    }
}