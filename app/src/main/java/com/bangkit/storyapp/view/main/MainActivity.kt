package com.bangkit.storyapp.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.bangkit.storyapp.R
import com.bangkit.storyapp.databinding.ActivityMainBinding
import com.bangkit.storyapp.databinding.StoryItemBinding
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.pref.UserPreference
import com.bangkit.storyapp.util.showError
import com.bangkit.storyapp.util.showLoading
import com.bangkit.storyapp.view.adapter.StoriesAdapter
import com.bangkit.storyapp.view.detail.StoryDetailActivity
import com.bangkit.storyapp.view.login.LoginActivity
import com.bangkit.storyapp.view.maps.MapsActivity
import com.bangkit.storyapp.view.post.PostStoryActivity

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
            if (it.isNullOrEmpty()) {
                Toast.makeText(this@MainActivity, "No story available :(", Toast.LENGTH_SHORT).show()
            }

            storiesAdapter.setStories(it)
        }

        viewModel.isError.observe(this) {
            showError(it, applicationContext, "Unable to fetch stories")
        }

        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

        binding.postStoryFab.setOnClickListener {
            startActivity(Intent(this, PostStoryActivity::class.java))
        }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mapsMenu -> {
                startActivity(Intent(this, MapsActivity::class.java))
                true
            }
            R.id.settingsMenu -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.logoutMenu -> {
                val pref = UserPreference(applicationContext)
                pref.clearUser()
                viewModel.getUserData()
                true
            }
            else -> true
        }
    }

    private fun setupRecyclerView() {
        storiesAdapter = StoriesAdapter(this@MainActivity)
        storiesAdapter.setOnClickedCallback(object : StoriesAdapter.OnClickedCallback {
            override fun onClicked(storyData: ListStoryItem?, appContext: Context, binding: StoryItemBinding) {
                val intent = Intent(appContext, StoryDetailActivity::class.java)
                intent.putExtra(StoryDetailActivity.STORY, storyData)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@MainActivity,
                        Pair(binding.imageViewItem, "profile"),
                        Pair(binding.textViewItemName, "name"),
                        Pair(binding.textViewItemDesc, "description"),
                    )

                startActivity(intent, optionsCompat.toBundle())
            }
        })
        binding.storyListRecyclerView.apply {
            adapter = storiesAdapter
        }
    }
}