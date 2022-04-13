package com.bangkit.storyapp.model.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
)
