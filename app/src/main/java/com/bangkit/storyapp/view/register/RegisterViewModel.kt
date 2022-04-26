package com.bangkit.storyapp.view.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.storyapp.data.Result
import com.bangkit.storyapp.model.ApiResponse
import com.bangkit.storyapp.model.register.RegisterRequest
import com.bangkit.storyapp.retrofit.RetrofitService
import com.bangkit.storyapp.util.wrapEspressoIdlingResource

class RegisterViewModel(private val retrofitService: RetrofitService) : ViewModel() {
    fun register(name: String, email: String, password: String): LiveData<Result<ApiResponse>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val registerData = RegisterRequest(name, email, password)
                val response = retrofitService.register(registerData)

                if (response.error == true) {
                    throw Exception("${response.message}")
                }

                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("RegisterViewModel", "register: ${e.message.toString()} ")
                emit(Result.Error(e.message.toString()))
            }
        }
    }
}