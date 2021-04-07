package com.petter.testapplication.service

import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.service.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailService @Inject constructor(private val api: ApiService) {

    suspend fun fetchPlaylistDetail(id: Int): Flow<Result<Playlist>> {
        return flow { emit(Result.success(api.fetchPlaylistDetail(id))) }.catch {
            emit(Result.failure(RuntimeException("Sometimes went wrong")))
        }
    }

}