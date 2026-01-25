package com.hashalbum.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hashalbum.app.R
import com.hashalbum.app.data.GalleryImage

class GalleryAdapter(
    private val onImageClick: (GalleryImage, Int) -> Unit
) : ListAdapter<GalleryImage, GalleryAdapter.ImageViewHolder>(ImageDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery_image, parent, false)
        return ImageViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image, position)
    }
    
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val remarkIndicator: View = itemView.findViewById(R.id.remarkIndicator)
        
        fun bind(image: GalleryImage, position: Int) {
            Glide.with(itemView.context)
                .load(image.uri)
                .override(200, 200)
                .centerCrop()
                .thumbnail(0.1f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.placeholder_image)
                .into(imageView)
            
            // Hide remark indicator by default - will be shown after hash check
            remarkIndicator.visibility = View.GONE
            
            itemView.setOnClickListener {
                onImageClick(image, position)
            }
        }
        
        fun showRemarkIndicator(show: Boolean) {
            remarkIndicator.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
    
    class ImageDiffCallback : DiffUtil.ItemCallback<GalleryImage>() {
        override fun areItemsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean {
            return oldItem.uri == newItem.uri
        }
        
        override fun areContentsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean {
            return oldItem == newItem
        }
    }
}
