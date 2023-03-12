package com.valkotova.testassignment.ui.home.items

import com.valkotova.testassignment.model.Latest
import com.valkotova.testassignment.ui.views.lists.Item

class LatestItem(val value : Latest) : Item {
    override fun areItemsTheSame(other: Item): Boolean {
        return (other as? FlashSaleItem)?.let{
            return value.imageUrl == other.value.imageUrl
        }?:false
    }

    override fun areContentsTheSame(other: Item): Boolean {
        return false
    }
}