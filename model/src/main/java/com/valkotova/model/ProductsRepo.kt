package com.valkotova.model

interface ProductsRepo {
    suspend fun getProductData() : Product

    suspend fun getLatest() : LatestList

    suspend fun getFlashSale() : FlashSaleList

    suspend fun getListOfWords() : ListOfWords
}