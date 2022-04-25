package com.bangkit.storyapp.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.storyapp.di.Injection
import com.bangkit.storyapp.view.main.MainViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            with(Injection) {
                return MainViewModel(provideRepository(context)) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}