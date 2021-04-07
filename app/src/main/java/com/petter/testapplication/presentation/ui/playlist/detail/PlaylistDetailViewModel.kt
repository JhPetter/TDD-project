package com.petter.testapplication.presentation.ui.playlist.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.repository.PlaylistDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaylistDetailViewModel(private val repository: PlaylistDetailRepository) : ViewModel() {
    private val _playlistDetailLiveData: MutableLiveData<Result<Playlist>> by lazy { MutableLiveData<Result<Playlist>>() }
    val playlistDetailLiveData: LiveData<Result<Playlist>> get() = _playlistDetailLiveData

    private val _loaderLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val loaderLiveData: LiveData<Boolean> get() = _loaderLiveData

    fun getPlaylistDetail(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _loaderLiveData.postValue(true)
            repository.getPlaylistDetail(id).collect {
                _playlistDetailLiveData.postValue(it)
                _loaderLiveData.postValue(false)
            }
        }
    }
}