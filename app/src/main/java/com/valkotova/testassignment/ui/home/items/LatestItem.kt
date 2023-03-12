package com.valkotova.testassignment.ui.home.items

import com.valkotova.model.Latest
import com.valkotova.presenter.views.lists.Item

class LatestItem(val value : Latest) : com.valkotova.presenter.views.lists.Item {
    override fun areItemsTheSame(other: com.valkotova.presenter.views.lists.Item): Boolean {
        return (other as? FlashSaleItem)?.let{
            return value.imageUrl == other.value.imageUrl
        }?:false
    }

    override fun areContentsTheSame(other: com.valkotova.presenter.views.lists.Item): Boolean {
        return false
    }
}