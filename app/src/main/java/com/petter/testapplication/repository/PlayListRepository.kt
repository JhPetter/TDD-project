package com.petter.testapplication.repository

import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.service.PlaylistService
import kotlinx.coroutines.flow.Flow

class PlayListRepository(private val service: PlaylistService) {
    suspend fun fetchPlaylist(): Flow<Result<List<Playlist>>> = service.fetchPlaylist()
}