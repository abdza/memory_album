package com.hashalbum.app.ui

import android.text.format.DateUtils
import android.view.LayoutInflater
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
import com.hashalbum.app.data.PathInfo
import com.hashalbum.app.data.SearchResultItem

class SearchResultAdapter(
    private val onItemClick: (SearchResultItem) -> Unit
) : ListAdapter<SearchResultItem, SearchResultAdapter.SearchResultViewHolder>(SearchResultDiffCallback()) {

    private val expandedItems = mutableSetOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnail: ImageView = itemView.findViewById(R.id.searchThumbnail)
        private val remarkText: TextView = itemView.findViewById(R.id.searchRemarkText)
        private val pathSummary: TextView = itemView.findViewById(R.id.pathSummary)
        private val togglePaths: TextView = itemView.findViewById(R.id.togglePaths)
        private val pathListContainer: LinearLayout = itemView.findViewById(R.id.pathListContainer)

        fun bind(item: SearchResultItem) {
            // Load thumbnail from first valid path, or fallback to first path
            val displayPath = item.paths.firstOrNull { it.isValid } ?: item.paths.firstOrNull()
            if (displayPath != null) {
                Glide.with(itemView.context)
                    .load(displayPath.uri)
                    .override(200, 200)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.placeholder_image)
                    .into(thumbnail)
            } else {
                thumbnail.setImageResource(R.drawable.placeholder_image)
            }

            remarkText.text = item.imageData.remark

            val validCount = item.paths.count { it.isValid }
            val totalCount = item.paths.size
            pathSummary.text = if (totalCount == 0) {
                itemView.context.getString(R.string.no_valid_paths)
            } else {
                "$validCount/$totalCount paths valid"
            }

            val isExpanded = expandedItems.contains(item.imageData.hash)
            pathListContainer.visibility = if (isExpanded) View.VISIBLE else View.GONE
            togglePaths.text = if (isExpanded) {
                itemView.context.getString(R.string.hide_paths)
            } else {
                itemView.context.getString(R.string.show_paths)
            }

            togglePaths.setOnClickListener {
                if (expandedItems.contains(item.imageData.hash)) {
                    expandedItems.remove(item.imageData.hash)
                } else {
                    expandedItems.add(item.imageData.hash)
                }
                notifyItemChanged(bindingAdapterPosition)
            }

            // Build path list
            pathListContainer.removeAllViews()
            if (isExpanded) {
                for (pathInfo in item.paths) {
                    val pathView = LayoutInflater.from(itemView.context)
                        .inflate(R.layout.item_path, pathListContainer, false)
                    bindPathView(pathView, pathInfo)
                    pathListContainer.addView(pathView)
                }
            }

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }

        private fun bindPathView(view: View, pathInfo: PathInfo) {
            val statusDot: View = view.findViewById(R.id.statusDot)
            val pathText: TextView = view.findViewById(R.id.pathText)
            val statusLabel: TextView = view.findViewById(R.id.statusLabel)

            statusDot.setBackgroundResource(
                if (pathInfo.isValid) R.drawable.status_dot_valid
                else R.drawable.status_dot_invalid
            )

            pathText.text = pathInfo.uri.lastPathSegment ?: pathInfo.uri.toString()

            val relativeTime = DateUtils.getRelativeTimeSpanString(
                pathInfo.lastSeen,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS
            )
            statusLabel.text = relativeTime
        }
    }

    class SearchResultDiffCallback : DiffUtil.ItemCallback<SearchResultItem>() {
        override fun areItemsTheSame(oldItem: SearchResultItem, newItem: SearchResultItem): Boolean {
            return oldItem.imageData.hash == newItem.imageData.hash
        }

        override fun areContentsTheSame(oldItem: SearchResultItem, newItem: SearchResultItem): Boolean {
            return oldItem == newItem
        }
    }
}
