package com.valkotova.testassignment.data

import com.valkotova.testassignment.model.FlashSaleList
import com.valkotova.testassignment.model.LatestList
import com.valkotova.testassignment.model.ListOfWords
import com.valkotova.testassignment.model.Product
import com.valkotova.testassignment.model.repository.ProductsRepo
import com.valkotova.testassignment.network.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductsRepoImpl @Inject constructor(val api : API) : ProductsRepo {
    override suspend fun getProductData(): Product {
        return withContext(Dispatchers.IO) {
            api.getProductData()
        }
    }

    override suspend fun getLatest(): LatestList {
        return withContext(Dispatchers.IO) {
            api.getLatest()
        }
    }

    override suspend fun getFlashSale(): FlashSaleList {
        return withContext(Dispatchers.IO) {
            api.getFlashSale()
        }
    }

    override suspend fun getListOfWords(): ListOfWords {
        return withContext(Dispatchers.IO) {
            api.getListOfWords()
        }
    }
}