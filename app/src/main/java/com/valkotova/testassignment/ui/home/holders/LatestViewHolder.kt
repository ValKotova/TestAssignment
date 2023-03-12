package com.valkotova.testassignment.ui.home.holders

import com.valkotova.presenter.di.loadGlide
import com.valkotova.testassignment.databinding.HolderLatestBinding
import com.valkotova.testassignment.ui.home.items.LatestItem
import com.valkotova.presenter.views.lists.BaseViewHolder
import java.util.*

class LatestViewHolder(private val binding: HolderLatestBinding) : BaseViewHolder<LatestItem>(binding.root)  {

    override fun bind(item : LatestItem){
        binding.ivBackground.loadGlide(item.value.imageUrl)
        binding.tvName.text = item.value.name
        binding.tvCategory.text = item.value.category
        binding.tvPrice.text = String.format(Locale.getDefault(), "%.2f", item.value.price)
    }

    override fun update(item: LatestItem) {
        binding.tvName.text = item.value.name
        binding.tvCategory.text = item.value.category
        binding.tvPrice.text = String.format(Locale.getDefault(), "%.2f", item.value.price)
    }
}