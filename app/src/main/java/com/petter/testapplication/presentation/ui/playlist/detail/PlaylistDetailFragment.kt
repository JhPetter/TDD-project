package com.petter.testapplication.presentation.ui.playlist.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.petter.testapplication.R
import com.petter.testapplication.databinding.FragmentPlaylistDetailBinding
import com.petter.testapplication.presentation.factory.PlaylistDetailViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {
    private val args: PlaylistDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentPlaylistDetailBinding

    @Inject
    lateinit var playlistDetailFactory: PlaylistDetailViewModelFactory
    private lateinit var playlistDetailViewModel: PlaylistDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = args.playlistId
        playlistDetailViewModel =
            ViewModelProvider(this, playlistDetailFactory).get(PlaylistDetailViewModel::class.java)
        playlistDetailViewModel.getPlaylistDetail(id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistDetailBinding.inflate(
            LayoutInflater.from(
                requireContext(),
            ), container,
            false
        )
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        playlistDetailViewModel.playlistDetailLiveData.observe(viewLifecycleOwner) {
            if (it.getOrNull() != null)
                binding.playlist = it.getOrNull()
            else
                Snackbar.make(binding.root, R.string.generic_error, Snackbar.LENGTH_LONG).show()
        }

        playlistDetailViewModel.loaderLiveData.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.playlistDetailLoader.visibility = View.VISIBLE
                else -> binding.playlistDetailLoader.visibility = View.GONE
            }
        }
    }
}