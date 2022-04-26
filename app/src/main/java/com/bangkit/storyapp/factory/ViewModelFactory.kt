package com.bangkit.storyapp.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.storyapp.di.Injection
import com.bangkit.storyapp.view.login.LoginViewModel
import com.bangkit.storyapp.view.main.MainViewModel
import com.bangkit.storyapp.view.maps.MapsViewModel
import com.bangkit.storyapp.view.register.RegisterViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            with(Injection) {
                return MainViewModel(provideRepository(context)) as T
            }
        } else if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            with(Injection) {
                return MapsViewModel(provideRepository(context)) as T
            }
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            with(Injection) {
                return LoginViewModel(provideService(context)) as T
            }
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            with(Injection) {
                return RegisterViewModel(provideService(context)) as T
            }
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}