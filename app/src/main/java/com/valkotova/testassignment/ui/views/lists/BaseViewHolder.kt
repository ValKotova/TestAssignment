package com.valkotova.testassignment.ui.views.lists

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.valkotova.testassignment.ui.views.lists.Item

abstract class BaseViewHolder<T : Item>(itemView : View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
    abstract fun update(item: T)
}