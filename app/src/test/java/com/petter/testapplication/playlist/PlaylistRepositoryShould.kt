package com.petter.testapplication.playlist

import com.petter.testapplication.BaseUnitTest
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.repository.PlayListRepository
import com.petter.testapplication.service.PlaylistService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistRepositoryShould : BaseUnitTest() {

    private val service: PlaylistService = mock()
    private val playlists = mock<List<Playlist>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun fetchPlaylistFromService() = runBlockingTest {
        val repository = PlayListRepository(service)
        repository.fetchPlaylist()
        verify(service, times(1)).fetchPlaylist()
    }

    @Test
    fun emitPlaylistFromService() = runBlockingTest {
        val repository = mockSuccessCase()
        assertEquals(playlists, repository.fetchPlaylist().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockErrorCase()
        assertEquals(exception, repository.fetchPlaylist().first().exceptionOrNull())
    }

    private fun mockErrorCase(): PlayListRepository {
        runBlocking {
            whenever(service.fetchPlaylist()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                })
        }
        return PlayListRepository(service)
    }

    private fun mockSuccessCase(): PlayListRepository {
        runBlocking {
            whenever(service.fetchPlaylist()).thenReturn(flow {
                emit(Result.success(playlists))
            })
        }
        return PlayListRepository(service)
    }
}