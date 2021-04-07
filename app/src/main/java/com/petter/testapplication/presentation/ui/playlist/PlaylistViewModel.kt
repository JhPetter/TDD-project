package com.petter.testapplication.presentation.ui.playlist

import androidx.lifecycle.*
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.repository.PlayListRepository
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PlaylistViewModel @Inject constructor(private val playListRepository: PlayListRepository) :
    ViewModel() {

    private val _loaderLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val loaderLiveData: LiveData<Boolean> get() = _loaderLiveData

    val playlistLiveData: LiveData<Result<List<Playlist>>> =
        liveData {
            _loaderLiveData.postValue(true)
            emitSource(playListRepository.fetchPlaylist()
                .onEach {
                    _loaderLiveData.postValue(false)
                }
                .asLiveData())
        }

}