package com.bangkit.storyapp.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

fun showLoading(isLoading: Boolean, progressBar: ProgressBar) {
    if (isLoading) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }
}

fun showError(isError: Boolean, context: Context, message: String) {
    if (isError) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}