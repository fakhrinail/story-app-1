package com.bangkit.storyapp.view.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.storyapp.model.UserModel
import com.bangkit.storyapp.pref.UserPreference

class MainViewModel : ViewModel() {
    private val _userModel = MutableLiveData<UserModel>()
    val userModel: LiveData<UserModel> = _userModel

    fun getUserData(context: Context) {
        val userPreference = UserPreference(context)
        _userModel.value = userPreference.getUser()
    }
}