package com.valkotova.testassignment.network

import com.valkotova.testassignment.model.FlashSaleListData
import com.valkotova.testassignment.model.LatestListData
import com.valkotova.testassignment.model.ListOfWordsData
import com.valkotova.testassignment.model.ProductData
import retrofit2.http.GET

interface API {
    @GET("f7f99d04-4971-45d5-92e0-70333383c239")
    suspend fun getProductData() : ProductData

    @GET("cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
    suspend fun getLatest() : LatestListData

    @GET("a9ceeb6e-416d-4352-bde6-2203416576ac")
    suspend fun getFlashSale() : FlashSaleListData

    @GET("4c9cd822-9479-4509-803d-63197e5a9e19")
    suspend fun getListOfWords() : ListOfWordsData
}