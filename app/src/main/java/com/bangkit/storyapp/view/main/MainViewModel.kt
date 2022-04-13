package com.bangkit.storyapp.view.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.storyapp.model.UserModel
import com.bangkit.storyapp.pref.UserPreference

class MainViewModel(val context: Context) : ViewModel() {
    private val userPreference = UserPreference(context)
    private val _userModel = MutableLiveData<UserModel>()
    val userModel: LiveData<UserModel> = _userModel

    fun getUserData() {
        _userModel.value = userPreference.getUser()
    }
}