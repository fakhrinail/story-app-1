package com.bangkit.storyapp.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.bangkit.storyapp.database.StoryDatabase
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.retrofit.RetrofitService

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
}