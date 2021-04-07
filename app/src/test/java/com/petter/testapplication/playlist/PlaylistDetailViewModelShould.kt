package com.petter.testapplication.playlist

import com.petter.testapplication.BaseUnitTest
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.presentation.ui.playlist.detail.PlaylistDetailViewModel
import com.petter.testapplication.repository.PlaylistDetailRepository
import com.petter.testapplication.utils.captureValues
import com.petter.testapplication.utils.getValueForTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistDetailViewModelShould : BaseUnitTest() {
    private val repository: PlaylistDetailRepository = mock()
    private val id: Int = 1
    private val playlist: Playlist = mock()
    private val expected = Result.success(playlist)
    private val exception = RuntimeException("Sometimes wrong")
    private val error = Result.failure<Playlist>(exception)

    @Test
    fun getPlaylistDetailFromRepository() = runBlockingTest {
        val viewModel: PlaylistDetailViewModel = mockSuccessCase()
        viewModel.getPlaylistDetail(id)
        viewModel.playlistDetailLiveData.getValueForTest()
        verify(repository, times(1)).getPlaylistDetail(id)
    }

    @Test
    fun emitsPlaylistDetailFromRepository() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.getPlaylistDetail(id)
        assertEquals(expected, viewModel.playlistDetailLiveData.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockErrorCase()
        viewModel.getPlaylistDetail(id)
        assertEquals(
            error,
            viewModel.playlistDetailLiveData.getValueForTest()
        )
    }

    @Test
    fun showSpinnerWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.loaderLiveData.captureValues {
            viewModel.getPlaylistDetail(id)
            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistDetailLoad() = runBlockingTest {
        val viewModel = mockSuccessCase()
        viewModel.loaderLiveData.captureValues {
            viewModel.getPlaylistDetail(id)
            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runBlockingTest {
        val viewModel = mockErrorCase()
        viewModel.loaderLiveData.captureValues {
            viewModel.getPlaylistDetail(id)
            assertEquals(false, values.last())
        }
    }

    private suspend fun mockSuccessCase(): PlaylistDetailViewModel {
        whenever(repository.getPlaylistDetail(id)).thenReturn(flow {
            emit(expected)
        })
        return PlaylistDetailViewModel(repository)
    }

    private suspend fun mockErrorCase(): PlaylistDetailViewModel {
        whenever(repository.getPlaylistDetail(id)).thenReturn(flow {
            emit(error)
        })
        return PlaylistDetailViewModel(repository)
    }
}