package com.valkotova.testassignment.model

import com.google.gson.annotations.SerializedName

data class FlashSaleListData(
    @SerializedName("flash_sale")
    val flashSale : List<FlashSaleData>
)