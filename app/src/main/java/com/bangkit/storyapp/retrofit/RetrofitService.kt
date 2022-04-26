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
    suspend fun register(
        @Body registerData: RegisterRequest
    ): ApiResponse

    @Headers("Content-Type: application/json", "No-Authentication: true")
    @POST("login")
    suspend fun login(
        @Body loginData: LoginRequest
    ): ApiResponse

    @GET("stories")
    suspend fun getSuspendedStories(
        @Query("location") location: Int,
    ): ApiResponse

    @GET("stories")
    suspend fun getPagingStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ApiResponse

    @Multipart
    @POST("stories")
    fun postStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") latitude: RequestBody?,
        @Part("lon") longitude: RequestBody?,
    ): Call<ApiResponse>
}