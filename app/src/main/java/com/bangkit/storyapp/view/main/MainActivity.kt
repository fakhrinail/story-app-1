package com.bangkit.storyapp.view.main

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bangkit.storyapp.R
import com.bangkit.storyapp.databinding.ActivityMainBinding
import com.bangkit.storyapp.helper.StoryDiffCallback
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.util.showError
import com.bangkit.storyapp.util.showLoading
import com.bangkit.storyapp.view.adapter.StoriesAdapter
import com.bangkit.storyapp.view.detail.StoryDetailActivity
import com.bangkit.storyapp.view.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storiesAdapter: StoriesAdapter
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        viewModel.getUserData()

        viewModel.userModel.observe(this) {
            if (it.token.isNullOrBlank()) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                viewModel.getStories()
            }
        }

        setupRecyclerView()

        viewModel.stories.observe(this) {
            storiesAdapter.setStories(it)
        }

        viewModel.isError.observe(this) {
            showError(it, applicationContext, "Unable to fetch stories")
        }

        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

        setContentView(binding.root)
    }

    private fun setupRecyclerView() {
        storiesAdapter = StoriesAdapter(this@MainActivity)
        storiesAdapter.setOnClickedCallback(object : StoriesAdapter.OnClickedCallback {
            override fun onClicked(storyData: ListStoryItem?, appContext: Context) {
                val intent = Intent(appContext, StoryDetailActivity::class.java)
                intent.putExtra(StoryDetailActivity.STORY, storyData)
                startActivity(intent, null)
            }
        })
        binding.storyListRecyclerView.apply {
            adapter = storiesAdapter
        }
    }
}