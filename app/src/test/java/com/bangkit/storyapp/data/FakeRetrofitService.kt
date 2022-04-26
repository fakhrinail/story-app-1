package com.bangkit.storyapp.data

import com.bangkit.storyapp.model.ApiResponse
import com.bangkit.storyapp.model.login.LoginRequest
import com.bangkit.storyapp.model.register.RegisterRequest
import com.bangkit.storyapp.retrofit.RetrofitService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class FakeRetrofitService: RetrofitService {
    override suspend fun register(registerData: RegisterRequest): ApiResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(loginData: LoginRequest): ApiResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getSuspendedStories(location: Int): ApiResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getPagingStories(page: Int, size: Int): ApiResponse {
        TODO("Not yet implemented")
    }

    override fun postStory(
        file: MultipartBody.Part,
        description: RequestBody,
        latitude: RequestBody?,
        longitude: RequestBody?
    ): Call<ApiResponse> {
        TODO("Not yet implemented")
    }
}