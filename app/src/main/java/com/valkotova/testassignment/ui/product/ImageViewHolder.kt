package com.valkotova.testassignment.ui.product

import com.valkotova.presenter.di.loadGlide
import com.valkotova.testassignment.databinding.HolderImagesBinding

class ImageViewHolder(val binding: HolderImagesBinding) : com.valkotova.presenter.views.lists.BaseViewHolder<ImageItem>(binding.root) {

    //    var wasAnimated = false
    override fun bind(item: ImageItem) {
//        wasAnimated = false
//        cancelAnimation()
        binding.ivImage.loadGlide(item.imageUrl)
        update(item)
    }

    override fun update(item: ImageItem) {
        if (item.isSelected) {
            //if(binding.root.scaleX < 1.1) {
                binding.root
                    .animate()
                    ?.translationZ(100f)
                    ?.scaleX(1.3f)
                    ?.scaleY(1.3f)
                    ?.setDuration(100)
                    ?.setUpdateListener {
                        binding.root.requestLayout()
                    }
                    ?.start()
            //}
            //wasAnimated = true
        } else {
            binding.root
                .animate()
                ?.translationZ(0f)
                ?.scaleX(1f)
                ?.scaleY(1f)
                ?.setDuration(100)
                ?.setUpdateListener {
                    binding.root.requestLayout()
                }
                ?.start()
            //wasAnimated = false
            //}
        }
    }


}