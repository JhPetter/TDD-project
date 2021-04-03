package com.petter.testapplication.playlist

import com.petter.testapplication.BaseUnitTest
import com.petter.testapplication.R
import com.petter.testapplication.entity.PlaylistRaw
import com.petter.testapplication.repository.mapper.PlaylistMapper
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PlaylistMapperShould : BaseUnitTest() {
    private val playlistRaw = PlaylistRaw(1, "Name", "default")
    private val playlistRawRock = PlaylistRaw(2, "Rock playlist", "rock")
    private val mapper = PlaylistMapper()
    private val playlists = mapper.invoke(listOf(playlistRaw, playlistRawRock))
    private val playlist = playlists[0]
    private val playlistRock = playlists[1]


    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.drawable.playlist, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.drawable.rock, playlistRock.image)
    }
}