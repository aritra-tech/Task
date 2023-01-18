package com.example.task_humaranagar

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.example.task_humaranagar.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private var imageURI: Uri? = null
    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.history.tooltipText = "Track Your Past Complaints"
        binding.addBtn.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            imagePickerActivityResult.launch(galleryIntent)
        }

        binding.delete.setOnClickListener {
            binding.image.visibility = View.GONE
            imageURI = null
        }

    }

    private var imagePickerActivityResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null) {
                val imageUri: Uri? = result.data?.data
                if (imageUri != null) {
                    imageURI = imageUri
                    binding.imagePreview.setImageURI(imageUri)
                    binding.image.visibility = View.VISIBLE
                    val fileName = File(imageUri.path).name
                    binding.imageTitle.text = renameTV(fileName.toString())
                }
            }
        }

    private fun renameTV(str: String) : String {

        return str.substring(0,6) + ".." + str.substring(str.length - 3)
    }
}