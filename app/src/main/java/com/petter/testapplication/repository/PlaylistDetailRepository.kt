package com.petter.testapplication.repository

import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.service.PlaylistDetailService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailRepository @Inject constructor(private val service: PlaylistDetailService) {
    suspend fun getPlaylistDetail(id: Int): Flow<Result<Playlist>> = service.fetchPlaylistDetail(id)
}