package com.hashalbum.app.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hashalbum.app.R
import com.hashalbum.app.data.MediaType

class MediaPagerAdapter(
    private val items: List<MediaItem>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_IMAGE = 0
        private const val VIEW_TYPE_VIDEO = 1
    }

    data class MediaItem(
        val uri: Uri,
        val mediaType: MediaType = MediaType.IMAGE,
        val duration: Long = 0L
    )

    override fun getItemViewType(position: Int): Int {
        return if (items[position].mediaType == MediaType.VIDEO) VIEW_TYPE_VIDEO else VIEW_TYPE_IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_VIDEO -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_video_pager, parent, false)
                VideoViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image_pager, parent, false)
                ImageViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> holder.bind(items[position].uri, position)
            is VideoViewHolder -> holder.bind(items[position].uri, position)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is VideoViewHolder) {
            holder.stopPlayback()
        }
        super.onViewRecycled(holder)
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.fullImageView)

        fun bind(uri: Uri, position: Int) {
            Glide.with(itemView.context)
                .load(uri)
                .fitCenter()
                .into(imageView)

            itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val videoView: VideoView = itemView.findViewById(R.id.videoView)
        private val playPauseButton: ImageView = itemView.findViewById(R.id.playPauseButton)
        private var isPlaying = false

        fun bind(uri: Uri, position: Int) {
            videoView.setVideoURI(uri)
            playPauseButton.visibility = View.VISIBLE
            isPlaying = false

            videoView.setOnPreparedListener { mp ->
                mp.isLooping = true
            }

            videoView.setOnClickListener {
                togglePlayback()
            }

            playPauseButton.setOnClickListener {
                togglePlayback()
            }

            itemView.setOnClickListener {
                onItemClick(position)
            }
        }

        private fun togglePlayback() {
            if (isPlaying) {
                videoView.pause()
                playPauseButton.visibility = View.VISIBLE
                isPlaying = false
            } else {
                videoView.start()
                playPauseButton.visibility = View.GONE
                isPlaying = true
            }
        }

        fun stopPlayback() {
            try {
                if (videoView.isPlaying) {
                    videoView.stopPlayback()
                }
            } catch (_: Exception) {}
            isPlaying = false
        }
    }
}
