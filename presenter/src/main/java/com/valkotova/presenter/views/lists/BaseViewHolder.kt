package com.valkotova.presenter.views.lists

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T : Item>(itemView : View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
    abstract fun update(item: T)
}