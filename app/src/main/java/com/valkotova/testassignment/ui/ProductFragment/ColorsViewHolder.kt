package com.valkotova.testassignment.ui.ProductFragment

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.valkotova.testassignment.R
import com.valkotova.testassignment.databinding.HolderColorBinding

class ColorsViewHolder(val binding : HolderColorBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : ColorItem){
        binding.ivColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(item.color))
        if(!item.isSelected)
            binding.root.background = ContextCompat.getDrawable(binding.root.context, R.drawable.background_color_selector)
        else
            binding.root.background = ContextCompat.getDrawable(binding.root.context, R.drawable.background_color_selector_selected)
    }

    fun update(item : ColorItem){
        if(!item.isSelected)
            binding.root.background = ContextCompat.getDrawable(binding.root.context, R.drawable.background_color_selector)
        else
            binding.root.background = ContextCompat.getDrawable(binding.root.context, R.drawable.background_color_selector_selected)
    }
}