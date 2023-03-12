package com.valkotova.testassignment.model.repository

import com.valkotova.testassignment.model.FlashSaleList
import com.valkotova.testassignment.model.LatestList
import com.valkotova.testassignment.model.ListOfWords
import com.valkotova.testassignment.model.Product

interface ProductsRepo {
    suspend fun getProductData() : Product

    suspend fun getLatest() : LatestList

    suspend fun getFlashSale() : FlashSaleList

    suspend fun getListOfWords() : ListOfWords
}