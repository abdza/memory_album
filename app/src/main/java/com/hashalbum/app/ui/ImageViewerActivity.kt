package com.hashalbum.app.ui

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.net.Uri
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.hashalbum.app.R
import com.hashalbum.app.databinding.ActivityImageViewerBinding
import com.hashalbum.app.util.ImageMetadata
import com.hashalbum.app.util.ImageMetadataHelper
import kotlinx.coroutines.launch
import kotlin.math.abs

class ImageViewerActivity : AppCompatActivity() {
    
    companion object {
        const val EXTRA_IMAGE_POSITION = "extra_image_position"
        const val EXTRA_IMAGE_URIS = "extra_image_uris"
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }
    
    private lateinit var binding: ActivityImageViewerBinding
    private val viewModel: GalleryViewModel by viewModels()
    
    private lateinit var imageAdapter: ImagePagerAdapter
    private var imageUris: List<Uri> = emptyList()
    private var currentPosition = 0
    
    private var isRemarkPanelShown = false
    private var isEditMode = false
    private var currentRemark = ""
    private var currentMetadata: ImageMetadata? = null
    private var remarkPanelAnimator: ObjectAnimator? = null

    private lateinit var gestureDetector: GestureDetectorCompat
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        parseIntent()
        setupViewPager()
        setupGestureDetection()
        setupRemarkPanel()
        setupCloseButton()
        
        // Load initial remark
        loadCurrentImageRemark()
    }
    
    private fun parseIntent() {
        currentPosition = intent.getIntExtra(EXTRA_IMAGE_POSITION, 0)
        val uriStrings = intent.getStringArrayListExtra(EXTRA_IMAGE_URIS) ?: arrayListOf()
        imageUris = uriStrings.map { Uri.parse(it) }
    }
    
    private fun setupViewPager() {
        imageAdapter = ImagePagerAdapter(imageUris) { position ->
            // Single tap - toggle UI visibility
            toggleUiVisibility()
        }
        
        binding.viewPager.apply {
            adapter = imageAdapter
            setCurrentItem(currentPosition, false)
            
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    currentPosition = position
                    hideRemarkPanel()
                    loadCurrentImageRemark()
                    updateCounter()
                }
            })
        }
        
        updateCounter()
    }
    
    private fun setupGestureDetection() {
        gestureDetector = GestureDetectorCompat(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 == null) return false
                
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                
                // Check for vertical swipe (up to show remark panel)
                if (abs(diffY) > abs(diffX) && 
                    abs(diffY) > SWIPE_THRESHOLD && 
                    abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    
                    if (diffY < 0) {
                        // Swipe up - show remark panel
                        showRemarkPanel()
                        return true
                    } else if (diffY > 0 && isRemarkPanelShown) {
                        // Swipe down - hide remark panel
                        hideRemarkPanel()
                        return true
                    }
                }
                
                return false
            }
        })
    }
    
    private fun setupRemarkPanel() {
        // Initially hide the remark panel below the screen
        binding.remarkPanel.post {
            binding.remarkPanel.translationY = binding.remarkPanel.height.toFloat()
        }

        // Handle remark input
        binding.remarkInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveCurrentRemark()
                hideKeyboard()
                true
            } else {
                false
            }
        }

        // Save button
        binding.saveRemarkButton.setOnClickListener {
            saveCurrentRemark()
            hideKeyboard()
            Toast.makeText(this, R.string.remark_saved, Toast.LENGTH_SHORT).show()
            // Switch back to display mode after saving, or hide panel if empty
            if (currentRemark.isNotBlank()) {
                prepareDisplayMode()
            } else {
                hideRemarkPanel()
            }
        }

        // Edit button - switch to edit mode
        binding.editRemarkButton.setOnClickListener {
            setRemarkEditMode()
        }

        // Handle swipe down on remark panel to hide
        binding.remarkPanelHandle.setOnClickListener {
            if (isRemarkPanelShown) {
                hideRemarkPanel()
            } else {
                showRemarkPanel()
            }
        }

        // Swipe hint
        binding.swipeHint.visibility = View.VISIBLE
    }

    private fun setRemarkDisplayMode() {
        prepareDisplayMode()
    }

    private fun setRemarkEditMode() {
        prepareEditMode()
        binding.remarkInput.requestFocus()

        // Show keyboard
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.remarkInput, InputMethodManager.SHOW_IMPLICIT)
    }
    
    private fun setupCloseButton() {
        binding.closeButton.setOnClickListener {
            finish()
        }
    }
    
    private fun showRemarkPanel() {
        if (isRemarkPanelShown) return
        isRemarkPanelShown = true
        
        remarkPanelAnimator?.cancel()
        
        binding.remarkPanel.visibility = View.VISIBLE
        remarkPanelAnimator = ObjectAnimator.ofPropertyValuesHolder(
            binding.remarkPanel,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f)
        ).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        
        binding.swipeHint.visibility = View.GONE
        
        // Focus on the input field
        binding.remarkInput.requestFocus()
    }
    
    private fun hideRemarkPanel() {
        if (!isRemarkPanelShown) return
        isRemarkPanelShown = false
        
        hideKeyboard()
        
        remarkPanelAnimator?.cancel()
        
        remarkPanelAnimator = ObjectAnimator.ofPropertyValuesHolder(
            binding.remarkPanel,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, binding.remarkPanel.height.toFloat())
        ).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        
        binding.swipeHint.visibility = View.VISIBLE
    }
    
    private fun loadCurrentImageRemark() {
        if (imageUris.isEmpty() || currentPosition >= imageUris.size) return

        val currentUri = imageUris[currentPosition]

        lifecycleScope.launch {
            // Load remark
            val remark = viewModel.getRemark(currentUri)
            currentRemark = remark
            binding.remarkInput.setText(remark)

            // Load metadata
            currentMetadata = ImageMetadataHelper.getMetadata(this@ImageViewerActivity, currentUri)
            updateMetadataDisplay()

            // Show indicator if there's an existing remark
            binding.remarkIndicator.visibility = if (remark.isNotBlank()) View.VISIBLE else View.GONE

            // Pre-set the correct mode but don't show the panel
            prepareDisplayMode()
        }
    }

    private fun updateMetadataDisplay() {
        val metadata = currentMetadata ?: return

        var hasAnyMetadata = false

        // Date taken
        if (metadata.dateTaken != null) {
            binding.dateTakenValue.text = metadata.dateTaken
            binding.dateRow.visibility = View.VISIBLE
            hasAnyMetadata = true
        } else {
            binding.dateRow.visibility = View.GONE
        }

        // Location
        if (metadata.location != null) {
            binding.locationValue.text = metadata.location
            binding.locationRow.visibility = View.VISIBLE
            hasAnyMetadata = true
        } else {
            binding.locationRow.visibility = View.GONE
        }

        // Camera
        if (metadata.camera != null) {
            binding.cameraValue.text = metadata.camera
            binding.cameraRow.visibility = View.VISIBLE
            hasAnyMetadata = true
        } else {
            binding.cameraRow.visibility = View.GONE
        }

        // Resolution
        if (metadata.resolution != null) {
            binding.resolutionValue.text = metadata.resolution
            binding.resolutionRow.visibility = View.VISIBLE
            hasAnyMetadata = true
        } else {
            binding.resolutionRow.visibility = View.GONE
        }

        // File size
        if (metadata.fileSize != null) {
            binding.fileSizeValue.text = metadata.fileSize
            binding.fileSizeRow.visibility = View.VISIBLE
            hasAnyMetadata = true
        } else {
            binding.fileSizeRow.visibility = View.GONE
        }

        // Show/hide metadata section
        binding.metadataSection.visibility = if (hasAnyMetadata) View.VISIBLE else View.GONE
    }

    private fun prepareDisplayMode() {
        // Prepare display mode without showing the panel
        isEditMode = false
        binding.remarkDisplayContainer.visibility = View.VISIBLE
        binding.remarkEditContainer.visibility = View.GONE

        if (currentRemark.isNotBlank()) {
            binding.remarkLabel.visibility = View.VISIBLE
            binding.remarkDisplay.text = currentRemark
            binding.remarkDisplay.visibility = View.VISIBLE
            binding.noRemarkText.visibility = View.GONE
            binding.editRemarkButton.text = getString(R.string.edit)
        } else {
            binding.remarkLabel.visibility = View.GONE
            binding.remarkDisplay.visibility = View.GONE
            binding.noRemarkText.visibility = View.VISIBLE
            binding.editRemarkButton.text = getString(R.string.add_remark)
        }
    }

    private fun prepareEditMode() {
        // Prepare edit mode without showing the panel
        isEditMode = true
        binding.remarkInput.setText(currentRemark)
        binding.remarkDisplayContainer.visibility = View.GONE
        binding.remarkEditContainer.visibility = View.VISIBLE
    }
    
    private fun saveCurrentRemark() {
        if (imageUris.isEmpty() || currentPosition >= imageUris.size) return

        val currentUri = imageUris[currentPosition]
        val remark = binding.remarkInput.text.toString()
        currentRemark = remark

        lifecycleScope.launch {
            viewModel.saveRemark(currentUri, remark)

            // Update indicator
            binding.remarkIndicator.visibility = if (remark.isNotBlank()) View.VISIBLE else View.GONE
        }
    }
    
    private fun updateCounter() {
        binding.imageCounter.text = "${currentPosition + 1} / ${imageUris.size}"
    }
    
    private fun toggleUiVisibility() {
        val isVisible = binding.topBar.visibility == View.VISIBLE
        val targetAlpha = if (isVisible) 0f else 1f
        val targetVisibility = if (isVisible) View.GONE else View.VISIBLE
        
        binding.topBar.animate()
            .alpha(targetAlpha)
            .setDuration(200)
            .withEndAction {
                binding.topBar.visibility = targetVisibility
                binding.topBar.alpha = 1f
            }
            .start()
        
        if (!isRemarkPanelShown) {
            binding.swipeHint.animate()
                .alpha(targetAlpha)
                .setDuration(200)
                .withEndAction {
                    binding.swipeHint.visibility = targetVisibility
                    binding.swipeHint.alpha = 1f
                }
                .start()
        }
    }
    
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.remarkInput.windowToken, 0)
    }
    
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // Let gesture detector handle the event first
        gestureDetector.onTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }
    
    override fun onBackPressed() {
        if (isRemarkPanelShown) {
            hideRemarkPanel()
        } else {
            super.onBackPressed()
        }
    }
}
