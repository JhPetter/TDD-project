package com.petter.testapplication.repository.mapper

import com.petter.testapplication.R
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.entity.PlaylistRaw
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistRaw>, List<Playlist>> {
    override fun invoke(playlistRows: List<PlaylistRaw>): List<Playlist> {
        return playlistRows.map {
            val image = when (it.category) {
                "rock" -> R.drawable.rock
                else -> R.drawable.playlist
            }
            Playlist(it.id, it.name, it.category, image)
        }
    }
}