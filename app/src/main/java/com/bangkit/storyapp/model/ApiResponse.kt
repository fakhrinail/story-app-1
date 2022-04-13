package com.bangkit.storyapp.model

import com.bangkit.storyapp.model.login.LoginResult
import com.bangkit.storyapp.model.story.ListStory
import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @field:SerializedName("error")
	val error: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("loginResult")
	val loginResult: LoginResult? = null,

    @field:SerializedName("listStory")
	val listStory: ListStory? = null,
)
