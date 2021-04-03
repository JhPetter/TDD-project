package com.petter.testapplication.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.petter.testapplication.databinding.FragmentPlaylistBinding
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.presentation.factory.PlaylistViewModelFactory
import com.petter.testapplication.repository.PlayListRepository
import com.petter.testapplication.service.PlaylistService
import com.petter.testapplication.service.api.ApiService

class PlaylistFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistBinding
    private val adapter: PlaylistAdapter by lazy { PlaylistAdapter() }
    private lateinit var playlistViewModelFactory: PlaylistViewModelFactory
    private lateinit var playlistViewModel: PlaylistViewModel

    private val service = PlaylistService(object:ApiService{
        override suspend fun fetchAllPlaylist(): List<Playlist> {
            TODO("Not yet implemented")
        }

    })
    private val repository = PlayListRepository(service)

    companion object {

        @JvmStatic
        fun newInstance() = PlaylistFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentPlaylistBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        setUpView()
        observeViewModels()
        return binding.root
    }

    private fun setUpView() {
        playlistViewModelFactory = PlaylistViewModelFactory(repository)
        playlistViewModel =
            ViewModelProvider(this, playlistViewModelFactory).get(PlaylistViewModel::class.java)
        binding.adapter = adapter
    }

    private fun observeViewModels() {
        playlistViewModel.playlistLiveData.observe(viewLifecycleOwner) {
            setUpList(it)
        }
    }

    private fun setUpList(resultPlaylist: Result<List<Playlist>>) {
        resultPlaylist.getOrNull()?.let {
            adapter.items = it
        }
    }
}