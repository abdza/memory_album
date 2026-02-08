package com.hashalbum.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hashalbum.app.R
import com.hashalbum.app.data.ContactWithCount

class ContactsAdapter(
    private val onContactClick: (ContactWithCount) -> Unit
) : ListAdapter<ContactWithCount, ContactsAdapter.ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contactName: TextView = itemView.findViewById(R.id.contactName)
        private val contactCount: TextView = itemView.findViewById(R.id.contactCount)

        fun bind(contact: ContactWithCount) {
            contactName.text = contact.name
            contactCount.text = "${contact.imageCount} images"
            itemView.setOnClickListener { onContactClick(contact) }
        }
    }

    class ContactDiffCallback : DiffUtil.ItemCallback<ContactWithCount>() {
        override fun areItemsTheSame(oldItem: ContactWithCount, newItem: ContactWithCount): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactWithCount, newItem: ContactWithCount): Boolean {
            return oldItem == newItem
        }
    }
}
