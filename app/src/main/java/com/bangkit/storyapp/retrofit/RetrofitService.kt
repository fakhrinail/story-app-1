package com.bangkit.storyapp.retrofit

import com.bangkit.storyapp.model.ApiResponse
import com.bangkit.storyapp.model.login.LoginRequest
import com.bangkit.storyapp.model.register.RegisterRequest
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @Headers("Content-Type: application/json")
    @POST("register")
    fun register(
        @Body registerData: RegisterRequest
    ): Call<ApiResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(
        @Body loginData: LoginRequest
    ): Call<ApiResponse>

//    @POST("stories")
//    fun postStory(
//        @Field("description") description: String,
//        @Field("photo") photo: File,
//        @Field("lat") lat: Float?,
//        @Field("lon") lon: Float?,
//    ): Call<ApiResponse>
}