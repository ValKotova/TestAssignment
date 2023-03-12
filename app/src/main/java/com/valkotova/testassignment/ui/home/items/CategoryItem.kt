package com.valkotova.testassignment.ui.home.items

import androidx.annotation.DrawableRes
import com.valkotova.presenter.views.lists.Item

class CategoryItem(
    val name : String,
    @DrawableRes val drawable : Int
) : Item {

    override fun areItemsTheSame(other: Item): Boolean {
        return (other as? CategoryItem)?.let{
            return name == other.name
        }?:false
    }

    override fun areContentsTheSame(other: Item): Boolean {
        return false
    }
}