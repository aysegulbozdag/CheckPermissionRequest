package com.example.checkpermissionrequest

import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.checkpermissionrequest.PermissionUtils.registerForActivityResult
import com.example.checkpermissionrequest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val checkVersion = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            android.Manifest.permission.READ_MEDIA_IMAGES else android.Manifest.permission.READ_EXTERNAL_STORAGE

        requestPermissionLauncher =
            this.registerForActivityResult(
                permissionGranted = {
                    //openCamera || openGallery
                    binding.textView.text = "PERMISSION GRANTED"
                },
                permissionDenied = {
                    binding.textView.text = "PERMISSION DENIED"
                })

        binding.button.setOnClickListener {
            requestPermissionLauncher.launch(checkVersion)
        }

        setContentView(binding.root)


    }


}