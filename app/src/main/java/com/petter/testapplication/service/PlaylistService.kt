package com.petter.testapplication.service

import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.entity.PlaylistRaw
import com.petter.testapplication.service.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class PlaylistService @Inject constructor(private val api: ApiService) {

    suspend fun fetchPlaylist(): Flow<Result<List<PlaylistRaw>>> {
        return flow { emit(Result.success(api.fetchAllPlaylist())) }.catch {
            emit(Result.failure(RuntimeException("Sometimes went wrong")))
        }
    }
}