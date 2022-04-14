package com.bangkit.storyapp.view.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.storyapp.R
import com.bangkit.storyapp.databinding.ActivityPostStoryBinding

class PostStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostStoryBinding.inflate(layoutInflater)
        supportActionBar?.title = "Post Story"

        setContentView(binding.root)
    }
}