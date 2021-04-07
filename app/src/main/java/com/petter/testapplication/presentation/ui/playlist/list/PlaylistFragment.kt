package com.petter.testapplication.presentation.ui.playlist.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.petter.testapplication.databinding.FragmentPlaylistBinding
import com.petter.testapplication.entity.Playlist
import com.petter.testapplication.presentation.factory.PlaylistViewModelFactory
import com.petter.testapplication.presentation.ui.playlist.PlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistBinding
    private val adapter: PlaylistAdapter by lazy {
        PlaylistAdapter {
            openDetail(it)
        }
    }

    @Inject
    lateinit var playlistViewModelFactory: PlaylistViewModelFactory
    private lateinit var playlistViewModel: PlaylistViewModel

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
        playlistViewModel =
            ViewModelProvider(this, playlistViewModelFactory).get(PlaylistViewModel::class.java)
        binding.adapter = adapter
    }

    private fun observeViewModels() {
        playlistViewModel.playlistLiveData.observe(viewLifecycleOwner) {
            setUpList(it)
        }
        playlistViewModel.loaderLiveData.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.playlistLoader.visibility = View.VISIBLE
                else -> binding.playlistLoader.visibility = View.GONE
            }

        }
    }

    private fun setUpList(resultPlaylist: Result<List<Playlist>>) {
        resultPlaylist.getOrNull()?.let {
            adapter.items = it
        }
    }

    private fun openDetail(id: Int) {
        val action = PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(id)
        findNavController().navigate(action)
    }
}