package com.petter.testapplication.repository

import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.repository.mapper.PlaylistMapper
import com.petter.testapplication.service.PlaylistService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class PlayListRepository @Inject constructor(
    private val service: PlaylistService,
    private val mapper: PlaylistMapper
) {
    suspend fun fetchPlaylist(): Flow<Result<List<Playlist>>> = service.fetchPlaylist().map {
        if (it.isSuccess)
            Result.success(mapper(it.getOrNull() ?: arrayListOf()))
        else
            Result.failure(it.exceptionOrNull() ?: Exception("Its local exception"))
    }
}