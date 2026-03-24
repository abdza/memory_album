package com.hashalbum.app.ui

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.hashalbum.app.R
import com.hashalbum.app.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings)

        val prefs = getSharedPreferences("album_prefs", MODE_PRIVATE)
        val currentColumns = prefs.getInt("grid_columns", 3)

        binding.seekbarColumns.min = 1
        binding.seekbarColumns.max = 5
        binding.seekbarColumns.progress = currentColumns
        updateColumnLabel(currentColumns)

        binding.seekbarColumns.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateColumnLabel(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                prefs.edit().putInt("grid_columns", seekBar.progress).apply()
            }
        })
    }

    private fun updateColumnLabel(columns: Int) {
        binding.textColumnsValue.text = columns.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
