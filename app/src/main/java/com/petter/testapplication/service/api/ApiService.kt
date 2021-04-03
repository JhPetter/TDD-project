package com.petter.testapplication.service.api

import com.petter.testapplication.entity.PlaylistRaw
import retrofit2.http.GET

interface ApiService {
    @GET("playlist")
    suspend fun fetchAllPlaylist(): List<PlaylistRaw>
}