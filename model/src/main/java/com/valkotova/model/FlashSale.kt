package com.valkotova.model

import com.google.gson.annotations.SerializedName

data class FlashSale(
    val category : String,
    val name : String,
    val price : Float,
    val discount : Int,
    @SerializedName("image_url")
    val imageUrl: String
)