package com.valkotova.testassignment.ui.home.holders

import com.valkotova.testassignment.databinding.HolderCategoryBinding
import com.valkotova.testassignment.ui.home.items.CategoryItem
import com.valkotova.presenter.views.lists.BaseViewHolder

class CategoryViewHolder(private val binding: HolderCategoryBinding) : com.valkotova.presenter.views.lists.BaseViewHolder<CategoryItem>(binding.root)  {
    override fun bind(item: CategoryItem) {
        binding.ivIcon.setImageResource(item.drawable)
        binding.tvText.text = item.name
    }

    override fun update(item: CategoryItem) {
    }

}