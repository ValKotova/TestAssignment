package com.valkotova.data

import com.valkotova.model.FlashSaleList
import com.valkotova.model.LatestList
import com.valkotova.model.ListOfWords
import com.valkotova.model.Product
import com.valkotova.model.ProductsRepo
import com.valkotova.network.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductsRepoImpl @Inject constructor(private val api : API) : ProductsRepo {
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