package com.petter.testapplication.playlist

import com.petter.testapplication.BaseUnitTest
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.repository.PlaylistDetailRepository
import com.petter.testapplication.service.PlaylistDetailService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistDetailRepositoryShould : BaseUnitTest() {
    private val service: PlaylistDetailService = mock()
    private val playlist: Playlist = mock()
    private val id: Int = 1
    private val exception = RuntimeException("Sometime wrong")

    @Test
    fun fetchPlaylistDetailFromService() = runBlockingTest {
        val repository = mockSuccessCase()
        repository.getPlaylistDetail(id)
        verify(service, times(1)).fetchPlaylistDetail(id)
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockErrorCase()
        assertEquals(exception, repository.getPlaylistDetail(id).first().exceptionOrNull())
    }

    private suspend fun mockSuccessCase(): PlaylistDetailRepository {
        whenever(service.fetchPlaylistDetail(id)).thenReturn(flow {
            emit(Result.success(playlist))
        })
        return PlaylistDetailRepository(service)
    }

    private suspend fun mockErrorCase(): PlaylistDetailRepository {
        whenever(service.fetchPlaylistDetail(id)).thenReturn(flow {
            emit(Result.failure<Playlist>(exception))
        })
        return PlaylistDetailRepository(service)
    }
}