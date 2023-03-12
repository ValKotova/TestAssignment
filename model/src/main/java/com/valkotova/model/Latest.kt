package com.valkotova.model

import com.google.gson.annotations.SerializedName

data class Latest(
    val category : String,
    val name : String,
    val price : Float,
    @SerializedName("image_url")
    val imageUrl : String
)