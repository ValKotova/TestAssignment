package com.valkotova.testassignment.model

import com.google.gson.annotations.SerializedName

data class LatestData(
    val category : String,
    val name : String,
    val price : Float,
    @SerializedName("image_url")
    val imageUrl : String
)