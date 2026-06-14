package com.example.moviemood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.moviemood.databinding.ActivityAdminBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                // Persistent permissions for custom gallery images
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                
                selectedImageUri = uri
                Glide.with(this)
                    .load(uri)
                    .into(binding.ivPreview)
                binding.ivPreview.visibility = View.VISIBLE
            } catch (e: Exception) {
                e.printStackTrace()
                // If persistent fails, just show it normally
                selectedImageUri = uri
                Glide.with(this)
                    .load(uri)
                    .into(binding.ivPreview)
                binding.ivPreview.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPickImage.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }

        binding.btnSaveMovie.setOnClickListener {
            saveMovie()
        }

        binding.btnManageMovies.setOnClickListener {
            startActivity(Intent(this, ManageMoviesActivity::class.java))
        }
    }

    private fun saveMovie() {
        val title = binding.etTitle.text.toString().trim()
        val synopsis = binding.etDescription.text.toString().trim()
        val rating = binding.etRating.text.toString().trim()
        val category = binding.spinnerCategory.selectedItem.toString()

        if (title.isEmpty() || synopsis.isEmpty() || rating.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val movie = MovieEntity(
            title = title,
            directorYear = "Admin Uploaded",
            synopsis = synopsis,
            rating = "⭐ $rating",
            imageUri = selectedImageUri?.toString(),
            category = category
        )

        lifecycleScope.launch(Dispatchers.IO) {
            AppDatabase.getDatabase(applicationContext).movieDao().insert(movie)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@AdminActivity, "Movie Added Successfully!", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        }
    }

    private fun clearFields() {
        binding.etTitle.text?.clear()
        binding.etDescription.text?.clear()
        binding.etRating.text?.clear()
        binding.ivPreview.visibility = View.GONE
        selectedImageUri = null
    }
}
