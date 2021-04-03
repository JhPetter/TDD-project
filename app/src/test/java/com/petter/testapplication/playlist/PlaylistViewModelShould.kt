package com.petter.testapplication.playlist

import com.petter.testapplication.BaseUnitTest
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.entity.PlaylistRaw
import com.petter.testapplication.presentation.ui.PlaylistViewModel
import com.petter.testapplication.repository.PlayListRepository
import com.petter.testapplication.utils.captureValues
import com.petter.testapplication.utils.getValueForTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistViewModelShould : BaseUnitTest() {

    private val repository: PlayListRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistFromRepository() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.playlistLiveData.getValueForTest()
        verify(repository, times(1)).fetchPlaylist()
    }

    @Test
    fun emitsPlaylistsFromRepository() = runBlockingTest {
        val viewModel = mockSuccessCase()
        assertEquals(expected, viewModel.playlistLiveData.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockFailureCase()
        assertEquals(exception, viewModel.playlistLiveData.getValueForTest()?.exceptionOrNull())
    }

    @Test
    fun showSpinnerWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.loaderLiveData.captureValues {
            viewModel.playlistLiveData.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistLoad() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.loaderLiveData.captureValues {
            viewModel.playlistLiveData.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runBlockingTest {
        val viewModel = mockFailureCase()
        viewModel.loaderLiveData.captureValues {
            viewModel.playlistLiveData.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    private fun mockFailureCase(): PlaylistViewModel {
        runBlocking {
            whenever(repository.fetchPlaylist()).thenReturn(flow {
                emit(Result.failure<List<Playlist>>(exception))
            })
        }
        return PlaylistViewModel(repository)
    }

    private fun mockSuccessCase(): PlaylistViewModel {
        runBlocking {
            whenever(repository.fetchPlaylist()).thenReturn(flow {
                emit(expected)
            })
        }

        return PlaylistViewModel(repository)
    }
}