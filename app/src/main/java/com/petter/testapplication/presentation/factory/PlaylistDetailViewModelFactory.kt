package com.petter.testapplication.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petter.testapplication.presentation.ui.playlist.detail.PlaylistDetailViewModel
import com.petter.testapplication.repository.PlaylistDetailRepository
import javax.inject.Inject

class PlaylistDetailViewModelFactory @Inject constructor(private val repository: PlaylistDetailRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaylistDetailViewModel(repository) as T
    }
}