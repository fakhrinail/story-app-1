package com.bangkit.storyapp.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.storyapp.data.Result
import com.bangkit.storyapp.model.login.LoginRequest
import com.bangkit.storyapp.model.user.UserModel
import com.bangkit.storyapp.retrofit.RetrofitService
import com.bangkit.storyapp.util.wrapEspressoIdlingResource

class LoginViewModel(private val retrofitService: RetrofitService) : ViewModel() {
    fun login(email: String, password: String): LiveData<Result<UserModel>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val loginData = LoginRequest(email, password)
                val response = retrofitService.login(loginData)

                val user = with(response.loginResult) {
                    UserModel(
                        this?.name, this?.token, this?.userId
                    )
                }

                emit(Result.Success(user))
            } catch (e: Exception) {
                Log.d("StoryRepository", "getStoriesWithLocation: ${e.message.toString()} ")
                emit(Result.Error(e.message.toString()))
            }
        }
    }
}