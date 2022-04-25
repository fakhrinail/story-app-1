package com.bangkit.storyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.storyapp.databinding.StoryItemBinding
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bumptech.glide.Glide

class StoriesAdapter(private val appContext: Context) : PagingDataAdapter<ListStoryItem, StoriesAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onClickedCallback: OnClickedCallback
    private var stories = ArrayList<ListStoryItem?>()

    fun setOnClickedCallback(onClickedCallback: OnClickedCallback) {
        this.onClickedCallback = onClickedCallback
    }

    inner class ViewHolder(val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            StoryItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val story = getItem(position)

        with(viewHolder.binding) {
            textViewItemName.text = story?.name
            textViewItemDesc.text = story?.description
            Glide.with(appContext).load(story?.photoUrl).into(imageViewItem)
            root.setOnClickListener {
                onClickedCallback.onClicked(story, appContext, this)
            }
        }
    }

    override fun getItemCount() = stories.size

    interface OnClickedCallback {
        fun onClicked(storyData: ListStoryItem?, appContext: Context, binding: StoryItemBinding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}