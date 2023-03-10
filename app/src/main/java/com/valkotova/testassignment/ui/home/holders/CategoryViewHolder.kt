package com.valkotova.testassignment.ui.home.holders

import androidx.recyclerview.widget.RecyclerView
import com.valkotova.testassignment.databinding.HolderCategoryBinding
import com.valkotova.testassignment.databinding.HolderFlashSaleBinding
import com.valkotova.testassignment.ui.views.lists.BaseViewHolder
import com.valkotova.testassignment.ui.home.items.CategoryItem

class CategoryViewHolder(private val binding: HolderCategoryBinding) : BaseViewHolder<CategoryItem>(binding.root)  {
    override fun bind(item: CategoryItem) {
        binding.ivIcon.setImageResource(item.drawable)
        binding.tvText.text = item.name
    }

    override fun update(item: CategoryItem) {
    }

}