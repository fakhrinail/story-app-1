package com.bangkit.storyapp.di

import android.content.Context
import com.bangkit.storyapp.data.StoryRepository
import com.bangkit.storyapp.database.StoryDatabase
import com.bangkit.storyapp.pref.UserPreference
import com.bangkit.storyapp.retrofit.RetrofitConfig
import com.bangkit.storyapp.retrofit.RetrofitService

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = RetrofitConfig.getApiService(context)
        return StoryRepository(database, apiService)
    }

    fun provideService(context: Context): RetrofitService {
        return RetrofitConfig.getApiService(context)
    }

    fun providePreferences(context: Context): UserPreference {
        return UserPreference(context)
    }
}