package com.bangkit.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.bangkit.storyapp.adapter.StoriesAdapter
import com.bangkit.storyapp.database.StoryDatabase
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.retrofit.RetrofitService
import com.bangkit.storyapp.util.DataDummy
import com.bangkit.storyapp.util.MainCoroutineRule
import com.bangkit.storyapp.util.getOrAwaitValue
import com.bangkit.storyapp.view.main.PagedTestDataSources
import com.bangkit.storyapp.view.main.noopListUpdateCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRules = MainCoroutineRule()

    @Mock
    private lateinit var retrofitService: RetrofitService
    private lateinit var storyDatabase: StoryDatabase
    private lateinit var storyRepository: StoryRepository


    @Before
    fun setUp() {
        storyRepository = StoryRepository(storyDatabase, retrofitService)
    }

    @Test
    fun `when GetStories Should Not Null and Return Success`() = mainCoroutineRules.runBlockingTest {
        val dummyStory = DataDummy.generateDummyStoryResponse()
        val data = PagedTestDataSources.snapshot(dummyStory)
        val story = MutableLiveData<PagingData<ListStoryItem>>()
        story.value = data

        Mockito.`when`(storyRepository.getStories()).thenReturn(story)
        val actualStories = storyRepository.getStories().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRules.dispatcher,
            workerDispatcher = mainCoroutineRules.dispatcher,
        )
        differ.submitData(actualStories)

        advanceUntilIdle()

        Mockito.verify(storyRepository).getStories()
        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0].id, differ.snapshot()[0]?.id)
    }

}