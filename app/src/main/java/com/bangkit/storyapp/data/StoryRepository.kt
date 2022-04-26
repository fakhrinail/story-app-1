package com.bangkit.storyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.bangkit.storyapp.database.StoryDatabase
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.retrofit.RetrofitService
import com.bangkit.storyapp.util.wrapEspressoIdlingResource

class StoryRepository(private val storyDatabase: StoryDatabase, private val retrofitService: RetrofitService) {
    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = QuoteRemoteMediator(storyDatabase, retrofitService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    fun getStoriesWithLocation(): LiveData<Result<List<ListStoryItem>?>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val response = retrofitService.getSuspendedStories(1)
                val stories = response.listStory
                emit(Result.Success(stories))
            } catch (e: Exception) {
                Log.d("StoryRepository", "getStoriesWithLocation: ${e.message.toString()} ")
                emit(Result.Error(e.message.toString()))
            }
        }
    }
}