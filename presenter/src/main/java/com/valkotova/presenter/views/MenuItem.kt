package com.valkotova.presenter.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.valkotova.presenter.R
import com.valkotova.presenter.databinding.HolderMenuItemBinding

class MenuItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val viewBinding = HolderMenuItemBinding.inflate(LayoutInflater.from(context), this, true)
    init {
        attrs?.let(this::applyAttrs)
    }
    private fun applyAttrs(attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MenuItem,
            0, 0
        )
        try {
            setText(a.getString(R.styleable.MenuItem_text) ?: "")
            setIcon(a.getDrawable(R.styleable.MenuItem_icon))
            setHasRightArrow(a.getBoolean(R.styleable.MenuItem_has_right_arrow, true))
        } finally {
            a.recycle()
        }
    }

    private fun setText(text: String) {
        viewBinding.tvText.text = text
    }

    private fun setIcon(drawable: Drawable?){
        viewBinding.ivIcon.setImageDrawable(drawable)
    }

    private fun setHasRightArrow(value: Boolean){
        viewBinding.ivRightArrow.visibility = if(value) View.VISIBLE else View.GONE
    }
}