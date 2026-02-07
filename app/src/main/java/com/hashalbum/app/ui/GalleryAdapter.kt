package com.hashalbum.app.ui

import android.net.Uri
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hashalbum.app.R
import com.hashalbum.app.data.GalleryImage
import com.hashalbum.app.data.GalleryItem

class GalleryAdapter(
    private val onImageClick: (GalleryImage, Int) -> Unit,
    private val onLongPress: ((GalleryImage, Int) -> Unit)? = null,
    private val onSelectionChanged: ((Int) -> Unit)? = null,
    private val onDoubleTap: ((GalleryImage, ImageViewHolder) -> Unit)? = null
) : ListAdapter<GalleryItem, RecyclerView.ViewHolder>(GalleryItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_IMAGE = 1
    }

    var isSelectionMode = false
        private set
    private val selectedItems = mutableSetOf<Uri>()
    private var overlayVisibleHolder: ImageViewHolder? = null

    fun enterSelectionMode() {
        isSelectionMode = true
    }

    fun exitSelectionMode() {
        isSelectionMode = false
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun toggleSelection(uri: Uri) {
        if (selectedItems.contains(uri)) {
            selectedItems.remove(uri)
        } else {
            selectedItems.add(uri)
        }
        onSelectionChanged?.invoke(selectedItems.size)
        notifyDataSetChanged()
    }

    fun clearSelection() {
        selectedItems.clear()
        onSelectionChanged?.invoke(0)
        notifyDataSetChanged()
    }

    fun getSelectedImages(): List<GalleryImage> {
        return currentList.filterIsInstance<GalleryItem.ImageItem>()
            .map { it.image }
            .filter { selectedItems.contains(it.uri) }
    }

    fun getSelectedCount(): Int = selectedItems.size

    fun hideCurrentOverlay() {
        overlayVisibleHolder?.hideInfoOverlay()
        overlayVisibleHolder = null
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is GalleryItem.DateHeader -> VIEW_TYPE_HEADER
            is GalleryItem.ImageItem -> VIEW_TYPE_IMAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_date_header, parent, false)
                DateHeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_gallery_image, parent, false)
                ImageViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is GalleryItem.DateHeader -> (holder as DateHeaderViewHolder).bind(item.label)
            is GalleryItem.ImageItem -> (holder as ImageViewHolder).bind(item.image, position)
        }
    }

    class DateHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.dateHeaderText)

        fun bind(label: String) {
            textView.text = label
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val remarkIndicator: View = itemView.findViewById(R.id.remarkIndicator)
        private val selectionOverlay: View = itemView.findViewById(R.id.selectionOverlay)
        private val checkIcon: View = itemView.findViewById(R.id.checkIcon)
        private val infoOverlay: LinearLayout = itemView.findViewById(R.id.infoOverlay)
        private val overlayRemark: TextView = itemView.findViewById(R.id.overlayRemark)
        private val overlayTags: TextView = itemView.findViewById(R.id.overlayTags)

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

            // Hide info overlay on rebind
            infoOverlay.visibility = View.GONE

            // Selection state
            val isSelected = selectedItems.contains(image.uri)
            selectionOverlay.visibility = if (isSelected) View.VISIBLE else View.GONE
            checkIcon.visibility = if (isSelected) View.VISIBLE else View.GONE

            val gestureDetector = GestureDetector(itemView.context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    if (overlayVisibleHolder != null) {
                        hideCurrentOverlay()
                    } else if (isSelectionMode) {
                        toggleSelection(image.uri)
                    } else {
                        onImageClick(image, position)
                    }
                    return true
                }

                override fun onDoubleTap(e: MotionEvent): Boolean {
                    if (!isSelectionMode) {
                        hideCurrentOverlay()
                        onDoubleTap?.invoke(image, this@ImageViewHolder)
                    }
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    if (!isSelectionMode) {
                        onLongPress?.invoke(image, position)
                    }
                }
            })

            itemView.setOnTouchListener { v, event ->
                gestureDetector.onTouchEvent(event)
                true
            }
        }

        fun showRemarkIndicator(show: Boolean) {
            remarkIndicator.visibility = if (show) View.VISIBLE else View.GONE
        }

        fun showInfoOverlay(remark: String, tags: List<String>) {
            val hasRemark = remark.isNotEmpty()
            val hasTags = tags.isNotEmpty()

            if (!hasRemark && !hasTags) {
                overlayRemark.text = itemView.context.getString(R.string.no_info)
                overlayRemark.visibility = View.VISIBLE
                overlayTags.visibility = View.GONE
            } else {
                if (hasRemark) {
                    overlayRemark.text = remark
                    overlayRemark.visibility = View.VISIBLE
                } else {
                    overlayRemark.visibility = View.GONE
                }

                if (hasTags) {
                    overlayTags.text = tags.joinToString("  ") { "#$it" }
                    overlayTags.visibility = View.VISIBLE
                } else {
                    overlayTags.visibility = View.GONE
                }
            }

            infoOverlay.visibility = View.VISIBLE
            overlayVisibleHolder = this
        }

        fun hideInfoOverlay() {
            infoOverlay.visibility = View.GONE
        }
    }

    class GalleryItemDiffCallback : DiffUtil.ItemCallback<GalleryItem>() {
        override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return when {
                oldItem is GalleryItem.DateHeader && newItem is GalleryItem.DateHeader ->
                    oldItem.label == newItem.label
                oldItem is GalleryItem.ImageItem && newItem is GalleryItem.ImageItem ->
                    oldItem.image.uri == newItem.image.uri
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem == newItem
        }
    }
}
