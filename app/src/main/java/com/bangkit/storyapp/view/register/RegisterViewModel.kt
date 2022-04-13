package com.bangkit.storyapp.view.register

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.storyapp.model.ApiResponse
import com.bangkit.storyapp.model.login.LoginRequest
import com.bangkit.storyapp.model.register.RegisterRequest
import com.bangkit.storyapp.model.user.UserModel
import com.bangkit.storyapp.pref.UserPreference
import com.bangkit.storyapp.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true

        val registerData = RegisterRequest(name, email, password)
        val client = RetrofitConfig.getApiService().register(registerData)
        client.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }
}