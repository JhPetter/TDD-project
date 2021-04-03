package com.petter.testapplication.service.api

import com.petter.testapplication.entity.Playlist

interface ApiService {

    suspend fun fetchAllPlaylist(): List<Playlist>
}