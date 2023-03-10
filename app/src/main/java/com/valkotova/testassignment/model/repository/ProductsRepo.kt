package com.valkotova.testassignment.model.repository

import com.valkotova.testassignment.model.FlashSaleListData
import com.valkotova.testassignment.model.LatestListData
import com.valkotova.testassignment.model.ListOfWordsData
import com.valkotova.testassignment.model.ProductData

interface ProductsRepo {
    suspend fun getProductData() : ProductData

    suspend fun getLatest() : LatestListData

    suspend fun getFlashSale() : FlashSaleListData

    suspend fun getListOfWords() : ListOfWordsData
}