package com.bangkit.storyapp.view.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.storyapp.model.ApiResponse
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.model.user.UserModel
import com.bangkit.storyapp.pref.UserPreference
import com.bangkit.storyapp.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _userModel = MutableLiveData<UserModel>()
    val userModel: LiveData<UserModel> = _userModel
    private val _stories = MutableLiveData<List<ListStoryItem?>?>()
    val stories: LiveData<List<ListStoryItem?>?> = _stories
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun getUserData() {
        val context = getApplication<Application>().applicationContext
        val userPreference = UserPreference(context)
        _userModel.value = userPreference.getUser()
    }

    fun getStories() {
        _isLoading.value = true

        val context = getApplication<Application>().applicationContext
        val client = RetrofitConfig.getApiService(context).getStories()
        client.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful

                Log.d("STORIES", response.body().toString())

                if (response.isSuccessful) {
                    _stories.value = response.body()?.listStory
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("STORIES", t.message.toString())
                _isLoading.value = false
                _isError.value = true
            }
        })
    }
}