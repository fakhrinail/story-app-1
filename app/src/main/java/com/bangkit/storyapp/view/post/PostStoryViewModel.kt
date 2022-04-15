package com.bangkit.storyapp.view.post

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.storyapp.model.ApiResponse
import com.bangkit.storyapp.retrofit.RetrofitConfig
import com.bangkit.storyapp.util.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PostStoryViewModel(): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun uploadImage(desc: String, getFile: File?, context: Context) {
        if (getFile != null && desc.isNotEmpty()) {
            _isLoading.value = true
            val file = reduceFileImage(getFile)

            val description = desc.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            val service = RetrofitConfig.getApiService(context).postStory(imageMultipart, description)

            service.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    _isError.value = !response.isSuccessful
                    _isLoading.value = false
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    _isError.value = true
                    _isLoading.value = false
                }
            })
        } else {
            _isError.value = true
        }
    }
}