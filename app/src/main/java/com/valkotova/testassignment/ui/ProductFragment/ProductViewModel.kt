package com.valkotova.testassignment.ui.ProductFragment

import androidx.lifecycle.ViewModel
import com.valkotova.testassignment.model.repository.ProductsRepo
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val productsRepo : ProductsRepo
) : ViewModel(){
}