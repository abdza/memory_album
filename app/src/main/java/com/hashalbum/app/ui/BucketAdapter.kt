package com.hashalbum.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hashalbum.app.R
import com.hashalbum.app.util.ImageBucket

class BucketAdapter(
    private val onBucketClick: (ImageBucket?) -> Unit
) : ListAdapter<ImageBucket, BucketAdapter.BucketViewHolder>(BucketDiffCallback()) {

    private var showAllImages = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BucketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bucket, parent, false)
        return BucketViewHolder(view)
    }

    override fun onBindViewHolder(holder: BucketViewHolder, position: Int) {
        val bucket = getItem(position)
        holder.bind(bucket)
    }

    fun setShowAllImages(show: Boolean) {
        showAllImages = show
    }

    inner class BucketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnail: ImageView = itemView.findViewById(R.id.bucketThumbnail)
        private val name: TextView = itemView.findViewById(R.id.bucketName)
        private val count: TextView = itemView.findViewById(R.id.bucketCount)

        fun bind(bucket: ImageBucket) {
            name.text = bucket.name
            count.text = "${bucket.imageCount} images"

            Glide.with(itemView.context)
                .load(bucket.thumbnailUri)
                .override(112, 112)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(thumbnail)

            itemView.setOnClickListener {
                onBucketClick(bucket)
            }
        }
    }

    class BucketDiffCallback : DiffUtil.ItemCallback<ImageBucket>() {
        override fun areItemsTheSame(oldItem: ImageBucket, newItem: ImageBucket): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageBucket, newItem: ImageBucket): Boolean {
            return oldItem == newItem
        }
    }
}
