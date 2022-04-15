package com.bangkit.storyapp.view.detail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangkit.storyapp.R
import com.bangkit.storyapp.databinding.ActivityStoryDetailBinding
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bumptech.glide.Glide

class StoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val story = intent.getParcelableExtra<ListStoryItem>(STORY) as ListStoryItem
        supportActionBar?.title = "Detail Story"

        binding = ActivityStoryDetailBinding.inflate(layoutInflater)

        with(binding) {
            nameTextView.text = story.name
            descTextView.text = story.description
            Glide.with(this@StoryDetailActivity).load(story.photoUrl)
                .into(storyDetailImageView)
        }

        setContentView(binding.root)
    }

    companion object {
        const val STORY = "story"
    }
}