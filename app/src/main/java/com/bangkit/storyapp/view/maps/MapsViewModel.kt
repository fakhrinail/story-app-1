package com.bangkit.storyapp.view.maps

import androidx.lifecycle.ViewModel
import com.bangkit.storyapp.data.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStoriesWithLocation() = storyRepository.getStoriesWithLocation()
}