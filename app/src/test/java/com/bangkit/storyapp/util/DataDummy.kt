package com.bangkit.storyapp.util

import com.bangkit.storyapp.model.ApiResponse
import com.bangkit.storyapp.model.login.LoginRequest
import com.bangkit.storyapp.model.login.LoginResult
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.model.user.UserModel

object DataDummy {
    fun generateDummySuccessResponseModel(): ApiResponse = ApiResponse(false, "success")

    fun generateDummyLoginSuccessResponseModel(): ApiResponse = ApiResponse(false, "success", LoginResult(
         "fakhri",
        "dummy-id",
         "dummy-token"
    ))

    fun generateDummyFailedResponseModel(): ApiResponse = ApiResponse(true, "Error message")

    fun generateDummyLoginRequestModel(): LoginRequest = LoginRequest("test@gmail.com", "test123")

    fun generateDummyUserModel(): UserModel = UserModel(
        name = "Fakhri Nail",
        token = "test123initoken",
        userId = "userid123iniuserid"
    )

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "link $i",
                "date $i",
                "name $i",
                "desc $i",
                i.toFloat(),
                i.toFloat(),
            )
            items.add(story)
        }
        return items
    }
}