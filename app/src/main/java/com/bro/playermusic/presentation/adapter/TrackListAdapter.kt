package com.bro.playermusic.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bro.playermusic.R
import com.bro.playermusic.domain.model.Track
import com.bumptech.glide.Glide

class TrackListAdapter :
    RecyclerView.Adapter<TrackListAdapter.LocalViewHolder>() {
    var data: List<Track> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class LocalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageTrack: ImageView = itemView.findViewById(R.id.coverArtImageView)
        val author: TextView = itemView.findViewById(R.id.trackAuthor)
        val title: TextView = itemView.findViewById(R.id.trackTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.track_item, parent, false)
        return LocalViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        data[position].let { trackItem ->
            holder.author.text = trackItem.author
            holder.title.text = trackItem.title
            Glide.with(holder.itemView.context)
                 .load(trackItem.imageUri)
                 .placeholder(R.drawable.default_music_image)
                 .error(R.drawable.default_music_image)
                 .into(holder.imageTrack)
        }
    }
}