package com.bangkit.storyapp.view.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.storyapp.model.ApiResponse
import com.bangkit.storyapp.model.login.LoginRequest
import com.bangkit.storyapp.model.user.UserModel
import com.bangkit.storyapp.pref.UserPreference
import com.bangkit.storyapp.retrofit.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun login(context: Context, email: String, password: String) {
        _isLoading.value = true

        val userPreference = UserPreference(context)
        val loginData = LoginRequest(email, password)
        val client = RetrofitConfig.getApiService().login(loginData)
        client.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                _isLoading.value = false
                _isError.value = !response.isSuccessful

                if (response.isSuccessful) {
                    val loginResult = response.body()?.loginResult
                    userPreference.setUser(
                        UserModel(
                            name = loginResult?.name,
                            userId = loginResult?.userId,
                            token = loginResult?.token
                        )
                    )
                    Toast.makeText(context, loginResult?.token, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }
}