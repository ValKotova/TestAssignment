package com.valkotova.testassignment.ui.home.holders

import androidx.recyclerview.widget.RecyclerView
import com.valkotova.testassignment.databinding.HolderLatestBinding
import com.valkotova.testassignment.di.loadGlide
import com.valkotova.testassignment.ui.views.lists.BaseViewHolder
import com.valkotova.testassignment.ui.home.items.LatestItem
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