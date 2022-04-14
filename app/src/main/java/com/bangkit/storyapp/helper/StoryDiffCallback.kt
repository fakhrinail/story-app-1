package com.bangkit.storyapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.storyapp.model.story.ListStoryItem

class StoryDiffCallback(
    private val oldStoryList: List<ListStoryItem?>,
    private val newStoryList: List<ListStoryItem?>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldStoryList.size
    }

    override fun getNewListSize(): Int {
        return newStoryList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldStoryList[oldItemPosition]?.id == newStoryList[newItemPosition]?.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProfile = oldStoryList[oldItemPosition]
        val newProfile = newStoryList[newItemPosition]

        return if (oldProfile !== null && newProfile !== null) {
            oldProfile.photoUrl == newProfile.photoUrl && oldProfile.name == newProfile.name
        } else !(oldProfile == null || newProfile == null)
    }
}