package com.bangkit.storyapp.pref

import com.bangkit.storyapp.model.user.UserModel

interface IUserPreferences {
    fun setUser(value: UserModel)
    fun clearUser()
    fun getUser(): UserModel
    fun getToken(): String?
}