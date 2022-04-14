package com.bangkit.storyapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.storyapp.databinding.StoryItemBinding
import com.bangkit.storyapp.helper.StoryDiffCallback
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bumptech.glide.Glide

class StoriesAdapter(private val appContext: Context) : RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {
    private lateinit var onClickedCallback: OnClickedCallback
    private var stories = ArrayList<ListStoryItem?>()

    fun setOnClickedCallback(onClickedCallback: OnClickedCallback) {
        this.onClickedCallback = onClickedCallback
    }

    fun setStories(stories: List<ListStoryItem?>?) {
        stories?.let {
            val diffCallback = StoryDiffCallback(this.stories.toList(), stories)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.stories.clear()
            this.stories.addAll(stories)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    inner class ViewHolder(val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            StoryItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val story = stories[position]

        with(viewHolder.binding) {
            textViewItemName.text = story?.name
            textViewItemDesc.text = story?.description
            Glide.with(appContext).load(story?.photoUrl).into(imageViewItem)
            root.setOnClickListener {
                onClickedCallback.onClicked(story, appContext)
            }
        }
    }

    override fun getItemCount() = stories.size

    interface OnClickedCallback {
        fun onClicked(profileData: ListStoryItem?, appContext: Context)
    }
}