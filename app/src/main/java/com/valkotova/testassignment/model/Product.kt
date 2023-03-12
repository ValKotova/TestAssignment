package com.valkotova.testassignment.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Product(
    val name : String,
    val description : String?,
    val rating : Float,
    @SerializedName("number_of_reviews")
    val numberOfReviews: Int,
    val price : Float,
    val colors : List<String>,
    @SerializedName("image_urls")
    val imageUrls : List<String>
) : Parcelable
