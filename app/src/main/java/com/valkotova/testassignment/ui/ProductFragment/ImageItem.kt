package com.valkotova.testassignment.ui.ProductFragment

import com.valkotova.testassignment.ui.views.lists.Item

data class ImageItem(val imageUrl : String, val isSelected : Boolean) : Item {
    override fun areItemsTheSame(other: Item): Boolean {
        return (other as? ImageItem)?.let{
            imageUrl == it.imageUrl && isSelected == it.isSelected
        } ?: run { false }
    }

    override fun areContentsTheSame(other: Item): Boolean {
        return (other as? ImageItem)?.let{
            isSelected == it.isSelected
        } ?: run { false }
    }
}