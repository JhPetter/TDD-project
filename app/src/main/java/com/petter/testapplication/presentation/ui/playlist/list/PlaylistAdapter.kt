package com.petter.testapplication.presentation.ui.playlist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petter.testapplication.BR
import com.petter.testapplication.databinding.PlaylistItemBinding
import com.petter.testapplication.entity.Playlist

class PlaylistAdapter(
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    var items: List<Playlist> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            PlaylistItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class PlaylistViewHolder(
        private val binding: PlaylistItemBinding,
        private val listener: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Playlist) {
            binding.setVariable(BR.playlist, item)
            binding.playlistImage.setImageResource(item.image)
            binding.root.setOnClickListener {
                listener(item.id)
            }
        }
    }
}