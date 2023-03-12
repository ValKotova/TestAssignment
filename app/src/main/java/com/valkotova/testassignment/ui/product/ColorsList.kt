package com.valkotova.testassignment.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.valkotova.testassignment.databinding.HolderColorBinding

class ColorsList : ListAdapter<ColorItem, ColorsViewHolder>(DiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        return ColorsViewHolder(
            HolderColorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    private var onClickItemListener : ((Int, ColorItem) -> Unit)? = null

    fun setItemOnClickListener(listener: (Int, ColorItem) -> Unit){
        onClickItemListener = listener
    }
    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.binding.root.setOnClickListener {
            onClickItemListener?.invoke(position, currentList[position])
        }
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int, payload : List<Any>) {
        if(payload.isEmpty())
            holder.bind(currentList[position])
        else{
            (payload[0] as? ColorItem)?.let{
                holder.update(it)
            }
        }
        holder.binding.root.setOnClickListener {
            onClickItemListener?.invoke(position, currentList[position])
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ColorItem>() {
        override fun areItemsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean {
            return oldItem.color == newItem.color && oldItem.isSelected == newItem.isSelected
        }

        override fun areContentsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }

        override fun getChangePayload(oldItem: ColorItem, newItem: ColorItem): Any? {
            return newItem
        }
    }
}