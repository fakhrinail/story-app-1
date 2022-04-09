package com.bangkit.storyapp.view.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bangkit.storyapp.R
import android.view.View as View1

class PasswordEditText : AppCompatEditText, View1.OnTouchListener {
    private lateinit var showTextButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun showShowTextButton() {
        setButtonDrawables(endOfTheText = showTextButtonImage)
    }

    private fun hideShowTextButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(v: View1?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val showTextButtonStart: Float
            val showTextButtonEnd: Float
            var isShowTextButtonClicked = false
            if (layoutDirection == View1.LAYOUT_DIRECTION_RTL) {
                showTextButtonEnd = (showTextButtonImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < showTextButtonEnd -> isShowTextButtonClicked = true
                }
            } else {
                showTextButtonStart =
                    (width - paddingEnd - showTextButtonImage.intrinsicWidth).toFloat()
                when {
                    event.x > showTextButtonStart -> isShowTextButtonClicked = true
                }
            }
            if (isShowTextButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        showTextButtonImage = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_baseline_eye_24
                        ) as Drawable
                        showShowTextButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        showTextButtonImage = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_baseline_eye_24
                        ) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideShowTextButton()
                        return true
                    }
                    else -> return false
                }
            } else {
                return false
            }
        }
        return false
    }


    private fun init() {
        showTextButtonImage =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_eye_24) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showShowTextButton() else hideShowTextButton()
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length <= 6) {
                    error = "Minimum password length is 6"
                }
            }
        })
    }
}