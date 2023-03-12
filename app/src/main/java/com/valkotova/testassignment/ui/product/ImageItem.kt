package com.valkotova.testassignment.ui.product

data class ImageItem(val imageUrl : String, val isSelected : Boolean) :
    com.valkotova.presenter.views.lists.Item {
    override fun areItemsTheSame(other: com.valkotova.presenter.views.lists.Item): Boolean {
        return (other as? ImageItem)?.let{
            imageUrl == it.imageUrl && isSelected == it.isSelected
        } ?: run { false }
    }

    override fun areContentsTheSame(other: com.valkotova.presenter.views.lists.Item): Boolean {
        return (other as? ImageItem)?.let{
            isSelected == it.isSelected
        } ?: run { false }
    }
}