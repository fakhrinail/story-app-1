package com.bangkit.storyapp.data

import androidx.paging.PagingSource
import com.bangkit.storyapp.database.StoryDao
import com.bangkit.storyapp.model.story.ListStoryItem

class FakeStoryDao: StoryDao {
    override suspend fun insertStory(story: List<ListStoryItem>) {
        TODO("Not yet implemented")
    }

    override fun getAllStory(): PagingSource<Int, ListStoryItem> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}