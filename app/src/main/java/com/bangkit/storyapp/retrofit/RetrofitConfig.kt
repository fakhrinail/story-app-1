package com.bangkit.storyapp.retrofit

import android.content.Context
import com.bangkit.storyapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {

    companion object {
        var BASE_URL = "https://story-api.dicoding.dev/v1/"

        fun getApiService(context: Context): RetrofitService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val serviceInterceptor = ServiceInterceptor(context)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(serviceInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }
}
