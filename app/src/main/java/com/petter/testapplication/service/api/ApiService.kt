package com.petter.testapplication.service.api

import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.entity.PlaylistRaw
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("playlist")
    suspend fun fetchAllPlaylist(): List<PlaylistRaw>

    @GET("playlist-details/{id}")
    suspend fun fetchPlaylistDetail(@Path("id") playlistId: Int): Playlist
}