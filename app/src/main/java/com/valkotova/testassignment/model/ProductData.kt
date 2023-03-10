package com.valkotova.testassignment.model

import com.google.gson.annotations.SerializedName

data class ProductData(
    val name : String,
    val description : String?,
    val rating : Float,
    val numberOfReviews: Int,
    val price : Float,
    val colors : List<String>,
    @SerializedName("image_urls")
    val imageUrls : List<String>
)
