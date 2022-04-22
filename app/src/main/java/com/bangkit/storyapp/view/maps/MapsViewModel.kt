package com.bangkit.storyapp.view.maps

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.storyapp.model.ApiResponse
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MapsViewModel(application: Application): AndroidViewModel(application) {
    private val _stories = MutableLiveData<List<ListStoryItem?>?>()
    val stories: LiveData<List<ListStoryItem?>?> = _stories
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun getStoriesWithLocation() {
        _isLoading.value = true

        val context = getApplication<Application>().applicationContext
        val client = RetrofitConfig.getApiService(context).getStories(1)
        client.enqueue(object : Callback<ApiResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful

                if (response.isSuccessful) {
                    _stories.value = response.body()?.listStory?.sortedBy {
                        LocalDate.parse(it.createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }
}