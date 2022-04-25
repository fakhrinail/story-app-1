package com.bangkit.storyapp.pref

import com.bangkit.storyapp.model.user.UserModel

class FakeUserPreferences: IUserPreferences {
    private var user: UserModel? = null

    override fun setUser(value: UserModel) {
        user = value
    }

    override fun clearUser() {
        user = null
    }

    override fun getUser(): UserModel {
        return user ?: UserModel(null, null, null)
    }

    override fun getToken(): String? {
        return user?.token
    }
}