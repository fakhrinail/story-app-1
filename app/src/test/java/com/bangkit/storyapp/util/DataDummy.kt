package com.bangkit.storyapp.util

import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.model.user.UserModel

object DataDummy {
    fun generateDummyUserModel(): UserModel {
        return UserModel(
            name = "Fakhri Nail",
            token = "test123initoken",
            userId = "userid123iniuserid"
        )
    }

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