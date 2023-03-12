package com.valkotova.testassignment.model

import com.google.gson.annotations.SerializedName

data class FlashSaleList(
    @SerializedName("flash_sale")
    val flashSale : List<FlashSale>
)