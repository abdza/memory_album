package com.hashalbum.app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashalbum.app.HashAlbumApp
import com.hashalbum.app.R
import com.hashalbum.app.data.ContactWithCount
import com.hashalbum.app.data.GalleryImage
import com.hashalbum.app.data.MediaType
import com.hashalbum.app.data.GalleryItem
import com.hashalbum.app.data.ImageRepository
import com.hashalbum.app.databinding.ActivityContactsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var repository: ImageRepository

    private var currentContact: ContactWithCount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = (application as HashAlbumApp).database
        repository = ImageRepository(
            database.imageDataDao(),
            database.imagePathDao(),
            database.imageTagDao(),
            database.contactDao()
        )

        setupToolbar()
        setupContactsList()
        loadContacts()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.contacts)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupContactsList() {
        contactsAdapter = ContactsAdapter { contact ->
            showContactImages(contact)
        }

        galleryAdapter = GalleryAdapter(
            onImageClick = { image, _ ->
                val imageItems = galleryAdapter.currentList.filterIsInstance<GalleryItem.ImageItem>()
                val imageUris = ArrayList(imageItems.map { it.image.uri.toString() })
                val mediaTypes = ArrayList(imageItems.map {
                    if (it.image.mediaType == MediaType.VIDEO) "video" else "image"
                })
                val durations = ArrayList(imageItems.map { it.image.duration.toString() })
                val imagePosition = imageItems.indexOfFirst { it.image.uri == image.uri }
                val intent = Intent(this, ImageViewerActivity::class.java).apply {
                    putExtra(ImageViewerActivity.EXTRA_IMAGE_POSITION, imagePosition.coerceAtLeast(0))
                    putStringArrayListExtra(ImageViewerActivity.EXTRA_IMAGE_URIS, imageUris)
                    putStringArrayListExtra(ImageViewerActivity.EXTRA_MEDIA_TYPES, mediaTypes)
                    putStringArrayListExtra(ImageViewerActivity.EXTRA_DURATIONS, durations)
                }
                startActivity(intent)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ContactsActivity)
            adapter = contactsAdapter
        }
    }

    private fun loadContacts() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            repository.getContactsWithCount().collectLatest { contacts ->
                binding.progressBar.visibility = View.GONE
                contactsAdapter.submitList(contacts)
                binding.emptyView.visibility = if (contacts.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun showContactImages(contact: ContactWithCount) {
        currentContact = contact
        supportActionBar?.title = contact.name
        binding.toolbar.navigationIcon = getDrawable(R.drawable.ic_close)

        binding.progressBar.visibility = View.VISIBLE
        binding.emptyView.visibility = View.GONE

        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (galleryAdapter.getItemViewType(position) == 0) 3 else 1
                }
        }
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = galleryAdapter

        lifecycleScope.launch {
            val matchedImages = withContext(Dispatchers.IO) {
                val hashes = repository.getImageHashesForContact(contact.id)
                val images = mutableListOf<GalleryImage>()
                for (hash in hashes) {
                    val paths = repository.getPathsForHashSync(hash)
                    val validPath = paths.firstOrNull { it.isValid } ?: paths.firstOrNull()
                    if (validPath != null) {
                        val uri = Uri.parse(validPath.path)
                        val dateModified = getDateModified(uri)
                        images.add(GalleryImage(uri = uri, dateModified = dateModified))
                    }
                }
                images.sortedByDescending { it.dateModified }
            }

            val items = groupImagesByDate(matchedImages)
            galleryAdapter.submitList(items)
            binding.progressBar.visibility = View.GONE
            binding.emptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showContactsList() {
        currentContact = null
        supportActionBar?.title = getString(R.string.contacts)
        binding.toolbar.navigationIcon = getDrawable(R.drawable.ic_close)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = contactsAdapter
    }

    private fun getDateModified(uri: Uri): Long {
        try {
            contentResolver.query(
                uri,
                arrayOf(MediaStore.Images.Media.DATE_MODIFIED),
                null, null, null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    return cursor.getLong(0)
                }
            }
        } catch (_: Exception) {}
        return 0L
    }

    private fun groupImagesByDate(images: List<GalleryImage>): List<GalleryItem> {
        if (images.isEmpty()) return emptyList()

        val todayLabel = getString(R.string.today)
        val yesterdayLabel = getString(R.string.yesterday)

        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val yesterday = Calendar.getInstance().apply {
            timeInMillis = today.timeInMillis
            add(Calendar.DAY_OF_YEAR, -1)
        }
        val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())

        val result = mutableListOf<GalleryItem>()
        var currentLabel: String? = null

        for (image in images) {
            val imageDate = Calendar.getInstance().apply {
                timeInMillis = image.dateModified * 1000
            }

            val label = when {
                image.dateModified == 0L -> getString(R.string.no_info)
                imageDate.timeInMillis >= today.timeInMillis -> todayLabel
                imageDate.timeInMillis >= yesterday.timeInMillis -> yesterdayLabel
                else -> dateFormat.format(Date(image.dateModified * 1000))
            }

            if (label != currentLabel) {
                result.add(GalleryItem.DateHeader(label))
                currentLabel = label
            }
            result.add(GalleryItem.ImageItem(image))
        }

        return result
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (currentContact != null) {
            showContactsList()
        } else {
            super.onBackPressed()
        }
    }
}
