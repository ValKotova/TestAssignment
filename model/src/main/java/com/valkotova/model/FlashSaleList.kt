package com.valkotova.model

import com.google.gson.annotations.SerializedName

data class FlashSaleList(
    @SerializedName("flash_sale")
    val flashSale : List<FlashSale>
)