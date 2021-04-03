package com.petter.testapplication.playlist

import com.petter.testapplication.BaseUnitTest
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.service.PlaylistService
import com.petter.testapplication.service.api.ApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val api: ApiService = mock()
    private val playlists = mock<List<Playlist>>()

    @Test
    fun fetchPlaylistFromApi() = runBlockingTest {
        service = PlaylistService(api)
        service.fetchPlaylist().first()
        verify(api, times(1)).fetchAllPlaylist()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessCase()
        assertEquals(Result.success(playlists), service.fetchPlaylist().first())
    }

    @Test
    fun emitsErrorsResultWhenNetworkFails() = runBlockingTest {
        mockFailureCase()
        assertEquals(
            "Sometimes went wrong",
            service.fetchPlaylist().first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockSuccessCase() {
        whenever(api.fetchAllPlaylist()).thenReturn(playlists)
        service = PlaylistService(api)
    }

    private suspend fun mockFailureCase() {
        whenever(api.fetchAllPlaylist()).thenThrow(RuntimeException("Damn backend develop exception"))
        service = PlaylistService(api)
    }
}