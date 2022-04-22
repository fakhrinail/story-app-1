package com.bangkit.storyapp.retrofit

import com.bangkit.storyapp.model.ApiResponse
import com.bangkit.storyapp.model.login.LoginRequest
import com.bangkit.storyapp.model.register.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @Headers("Content-Type: application/json", "No-Authentication: true")
    @POST("register")
    fun register(
        @Body registerData: RegisterRequest
    ): Call<ApiResponse>

    @Headers("Content-Type: application/json", "No-Authentication: true")
    @POST("login")
    fun login(
        @Body loginData: LoginRequest
    ): Call<ApiResponse>

    @GET("stories")
    fun getStories(
        @Query("location") location: Int,
    ):Call<ApiResponse>

    @Multipart
    @POST("stories")
    fun postStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<ApiResponse>
}