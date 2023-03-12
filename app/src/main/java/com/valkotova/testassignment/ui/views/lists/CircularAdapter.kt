package com.valkotova.testassignment.ui.views.lists

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class CircularAdapter<T : Item, VH : BaseViewHolder<T>>():
    ListAdapter<T, BaseViewHolder<T>>(object : DiffCallback<T>(){}) {

    var onClickItemListener : ((Int, T) -> Unit)? = null
    fun setItemOnClickListener(listener : (Int, T) -> Unit){
        onClickItemListener = listener
    }

    override fun getItem(position: Int): T {
        return currentList[position % currentList.size]
    }

    override fun getItemCount(): Int {
        return if(currentList.isEmpty())
            0
        else Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        currentList[position%currentList.size].let{
            holder.bind(it)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int, payload : List<Any>) {
        if(payload.isEmpty())
            holder.bind(currentList[position%currentList.size])
        else{
            (payload[0] as? T)?.let{
                holder.update(it)
            }
        }
        onClickItemListener?.let{ listener ->
            holder.itemView.rootView.setOnClickListener {
                listener.invoke(position%currentList.size, currentList[position%currentList.size])
            }
        }
    }

    abstract class DiffCallback<T : Item> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return newItem.areItemsTheSame(oldItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return newItem.areContentsTheSame(oldItem)
        }

        override fun getChangePayload(oldItem: T, newItem: T): Any? {
            return newItem
        }
    }
}