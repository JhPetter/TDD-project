package com.petter.testapplication.playlist

import com.petter.testapplication.BaseUnitTest
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.service.PlaylistDetailService
import com.petter.testapplication.service.api.ApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.lang.RuntimeException

class PlaylistDetailServiceShould : BaseUnitTest() {
    private lateinit var service: PlaylistDetailService
    private val api: ApiService = mock()
    private val playlist: Playlist = mock()
    private val id: Int = 1

    @Test
    fun fetchPlaylistDetailFromApi() = runBlockingTest {
        service = PlaylistDetailService(api)
        service.fetchPlaylistDetail(id).single()
        verify(api, times(1)).fetchPlaylistDetail(id)
    }

    @Test
    fun convertValueToFlowResultAndEmitsIt() = runBlockingTest {
        mockSuccessCase()
        assertEquals(Result.success(playlist), service.fetchPlaylistDetail(id).single())
    }


    @Test
    fun emitsErrorsResultWhenNetworkFails() = runBlockingTest {
        whenever(api.fetchPlaylistDetail(id)).thenThrow(RuntimeException("Sometimes went wrong"))
        service = PlaylistDetailService(api)
        assertEquals(
            "Sometimes went wrong",
            service.fetchPlaylistDetail(id).first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockSuccessCase() {
        whenever(api.fetchPlaylistDetail(id)).thenReturn(playlist)
        service = PlaylistDetailService(api)
    }
}