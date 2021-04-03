package com.petter.testapplication.playlist

import com.petter.testapplication.BaseUnitTest
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.entity.PlaylistRaw
import com.petter.testapplication.repository.PlayListRepository
import com.petter.testapplication.repository.mapper.PlaylistMapper
import com.petter.testapplication.service.PlaylistService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistRepositoryShould : BaseUnitTest() {

    private val service: PlaylistService = mock()
    private val playlists = mock<List<Playlist>>()
    private val playlistsRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something went wrong")
    private val mapper: PlaylistMapper = mock()

    @Test
    fun fetchPlaylistFromService() = runBlockingTest {
        val repository = mockSuccessCase()
        repository.fetchPlaylist()
        verify(service, times(1)).fetchPlaylist()
    }

    @Test
    fun emitMappedPlaylistFromService() = runBlockingTest {
        val repository = mockSuccessCase()
        assertEquals(playlists, repository.fetchPlaylist().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockErrorCase()
        assertEquals(exception, repository.fetchPlaylist().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        val repository = mockSuccessCase()
        repository.fetchPlaylist().first()
        verify(mapper, times(1)).invoke(playlistsRaw)
    }

    private suspend fun mockErrorCase(): PlayListRepository {
        whenever(service.fetchPlaylist()).thenReturn(
            flow {
                emit(Result.failure<List<PlaylistRaw>>(exception))
            })

        return PlayListRepository(service, mapper)
    }

    private suspend fun mockSuccessCase(): PlayListRepository {
        whenever(service.fetchPlaylist()).thenReturn(flow {
            emit(Result.success(playlistsRaw))
        })
        whenever(mapper.invoke(playlistsRaw)).thenReturn(playlists)

        return PlayListRepository(service, mapper)
    }
}