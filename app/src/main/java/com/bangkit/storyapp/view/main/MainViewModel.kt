package com.bangkit.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.storyapp.data.StoryRepository
import com.bangkit.storyapp.model.story.ListStoryItem

class MainViewModel(storyRepository: StoryRepository) : ViewModel() {
    val story: LiveData<PagingData<ListStoryItem>> = storyRepository.getStories().cachedIn(viewModelScope)
}