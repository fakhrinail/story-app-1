package com.bangkit.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.storyapp.data.StoryRepository
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.model.user.UserModel
import com.bangkit.storyapp.pref.UserPreference

class MainViewModel(private val userPreference: UserPreference, storyRepository: StoryRepository) : ViewModel() {
    private val _userModel = MutableLiveData<UserModel>()
    val userModel: LiveData<UserModel> = _userModel
    val story: LiveData<PagingData<ListStoryItem>> = storyRepository.getStories().cachedIn(viewModelScope)

    fun getUserData() {
        _userModel.value = userPreference.getUser()
    }
}