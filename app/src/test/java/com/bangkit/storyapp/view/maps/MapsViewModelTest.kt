package com.bangkit.storyapp.view.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bangkit.storyapp.data.Result
import com.bangkit.storyapp.data.StoryRepository
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.util.DataDummy
import com.bangkit.storyapp.util.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyStories = DataDummy.generateDummyStoryResponse()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `when Get HeadlineNews Should Not Null and Return Success`() {
        val expectedStories = MutableLiveData<Result<List<ListStoryItem>?>>()
        expectedStories.value = Result.Success(dummyStories)

        Mockito.`when`(mapsViewModel.getStoriesWithLocation()).thenReturn(expectedStories)

        val actualStories = mapsViewModel.getStoriesWithLocation().getOrAwaitValue()
        Mockito.verify(storyRepository).getStoriesWithLocation()
        Assert.assertNotNull(actualStories)
        Assert.assertTrue(actualStories is Result.Success)
        Assert.assertEquals(dummyStories.size, (actualStories as Result.Success).data?.size)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val stories = MutableLiveData<Result<List<ListStoryItem>?>>()
        stories.value = Result.Error("Error")
        Mockito.`when`(mapsViewModel.getStoriesWithLocation()).thenReturn(stories)
        val actualStories = mapsViewModel.getStoriesWithLocation().getOrAwaitValue()
        Mockito.verify(storyRepository).getStoriesWithLocation()
        Assert.assertNotNull(actualStories)
        Assert.assertTrue(actualStories is Result.Error)
    }
}