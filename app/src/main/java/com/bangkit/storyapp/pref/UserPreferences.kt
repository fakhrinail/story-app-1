package com.bangkit.storyapp.pref

import android.content.Context
import com.bangkit.storyapp.model.user.UserModel

class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val USER_ID = "userId"
        private const val TOKEN = "token"
    }
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    fun setUser(value: UserModel) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(USER_ID, value.userId)
        editor.putString(TOKEN, value.token)
        editor.apply()
    }


    fun clearUser() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }


    fun getToken(): String? {
        return preferences.getString(TOKEN, "")
    }
}