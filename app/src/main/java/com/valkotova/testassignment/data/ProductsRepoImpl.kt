package com.valkotova.testassignment.data

import com.valkotova.testassignment.model.FlashSaleListData
import com.valkotova.testassignment.model.LatestListData
import com.valkotova.testassignment.model.ListOfWordsData
import com.valkotova.testassignment.model.ProductData
import com.valkotova.testassignment.model.repository.ProductsRepo
import com.valkotova.testassignment.network.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductsRepoImpl @Inject constructor(val api : API) : ProductsRepo {
    override suspend fun getProductData(): ProductData {
        return withContext(Dispatchers.IO) {
            api.getProductData()
        }
    }

    override suspend fun getLatest(): LatestListData {
        return withContext(Dispatchers.IO) {
            api.getLatest()
        }
    }

    override suspend fun getFlashSale(): FlashSaleListData {
        return withContext(Dispatchers.IO) {
            api.getFlashSale()
        }
    }

    override suspend fun getListOfWords(): ListOfWordsData {
        return withContext(Dispatchers.IO) {
            api.getListOfWords()
        }
    }
}