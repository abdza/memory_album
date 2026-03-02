package com.hashalbum.app.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hashalbum.app.HashAlbumApp
import com.hashalbum.app.R
import com.hashalbum.app.data.ImageRepository
import com.hashalbum.app.databinding.ActivityBackupBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BackupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBackupBinding
    private lateinit var repository: ImageRepository

    private val createDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri: Uri? ->
        if (uri != null) {
            performExport(uri)
        }
    }

    private val openDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            performImport(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBackupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = (application as HashAlbumApp).database
        repository = ImageRepository(
            database.imageDataDao(),
            database.imagePathDao(),
            database.imageTagDao(),
            database.contactDao()
        )

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnExport.setOnClickListener {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            createDocumentLauncher.launch("memory_album_backup_$timestamp.json")
        }

        binding.btnImport.setOnClickListener {
            openDocumentLauncher.launch(arrayOf("application/json", "*/*"))
        }
    }

    private fun performExport(uri: Uri) {
        setLoading(true, getString(R.string.exporting))
        lifecycleScope.launch {
            try {
                val (json, imageCount, contactCount) = withContext(Dispatchers.IO) {
                    val backup = repository.exportBackupData()
                    val j = com.hashalbum.app.util.BackupManager.toJson(backup)
                    Triple(j, backup.images.size, backup.contacts.size)
                }
                withContext(Dispatchers.IO) {
                    contentResolver.openOutputStream(uri)?.use { out ->
                        out.write(json.toByteArray(Charsets.UTF_8))
                    }
                }
                setLoading(false)
                Toast.makeText(
                    this@BackupActivity,
                    getString(R.string.export_success, imageCount, contactCount),
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                setLoading(false)
                Toast.makeText(this@BackupActivity, R.string.export_failed, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun performImport(uri: Uri) {
        setLoading(true, getString(R.string.importing))
        lifecycleScope.launch {
            try {
                val json = withContext(Dispatchers.IO) {
                    contentResolver.openInputStream(uri)?.use { it.readBytes().toString(Charsets.UTF_8) }
                        ?: throw IllegalStateException("Could not open file")
                }
                val result = withContext(Dispatchers.IO) { repository.importData(json) }
                setLoading(false)
                Toast.makeText(
                    this@BackupActivity,
                    getString(R.string.import_success, result.imagesRestored, result.contactsRestored),
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                setLoading(false)
                Toast.makeText(this@BackupActivity, R.string.import_failed, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setLoading(loading: Boolean, message: String = "") {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.tvStatus.visibility = if (loading) View.VISIBLE else View.GONE
        binding.tvStatus.text = message
        binding.btnExport.isEnabled = !loading
        binding.btnImport.isEnabled = !loading
    }
}
