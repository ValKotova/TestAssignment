package com.valkotova.testassignment.ui.home.holders

import androidx.recyclerview.widget.RecyclerView
import com.valkotova.testassignment.R
import com.valkotova.testassignment.databinding.HolderFlashSaleBinding
import com.valkotova.testassignment.di.loadGlide
import com.valkotova.testassignment.ui.views.lists.BaseViewHolder
import com.valkotova.testassignment.ui.views.lists.Item
import com.valkotova.testassignment.ui.home.items.FlashSaleItem
import java.util.*

class FlashSaleViewHolder(private val binding: HolderFlashSaleBinding) : BaseViewHolder<FlashSaleItem>(binding.root)  {

    override fun bind(item : FlashSaleItem){
            binding.ivBackground.loadGlide(item.value.imageUrl)
            binding.tvName.text = item.value.name
            binding.tvCategory.text = item.value.category
            binding.tvPrice.text = String.format(Locale.getDefault(), "%.2f $", item.value.price)
            binding.tvDiscount.text = binding.root.resources.getString(R.string.discount_value, item.value.discount)
    }

    override fun update(item: FlashSaleItem) {
        binding.tvName.text = item.value.name
        binding.tvCategory.text = item.value.category
        binding.tvPrice.text = String.format(Locale.getDefault(), "%.2f $", item.value.price)
        binding.tvDiscount.text = binding.root.resources.getString(R.string.discount_value, item.value.discount)
    }
}