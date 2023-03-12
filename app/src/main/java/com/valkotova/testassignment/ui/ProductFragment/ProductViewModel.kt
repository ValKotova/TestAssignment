package com.valkotova.testassignment.ui.ProductFragment

import androidx.lifecycle.*
import com.valkotova.testassignment.model.Product
import com.valkotova.testassignment.model.repository.ProductsRepo
import com.valkotova.testassignment.ui.ext.UIError
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val productsRepo : ProductsRepo
) : ViewModel(){
    private val _state : MutableLiveData<ProductStates> = MutableLiveData(ProductStates.Empty)
    val state : LiveData<ProductStates>
        get() = _state

    private val _product : MutableLiveData<Product?> = MutableLiveData(null)
    val product : LiveData<Product?>
    get() = _product

    private val _colorsList : MutableLiveData<MutableList<ColorItem>> = MutableLiveData(mutableListOf())
    val colorsList : LiveData<MutableList<ColorItem>>
    get() = _colorsList
    private var selectedColorPosition : Int? = null

    private val _imagesList : MutableLiveData<MutableList<ImageItem>> = MutableLiveData(mutableListOf())
    val imagesList : LiveData<MutableList<ImageItem>>
        get() = _imagesList
    private var selectedImagePosition : Int? = null
    private val _selectedImage : MutableLiveData<String?> = MutableLiveData(null)
    val selectedImage : LiveData<String?>
    get() = _selectedImage

    private val _total : MutableLiveData<Float> = MutableLiveData(0f)
    val total : LiveData<Float>
    get() = _total

    init{
        viewModelScope.launch {
            try {
                val product = productsRepo.getProductData()
                setProduct(product)
            } catch (t: Throwable) {
                _state.postValue(ProductStates.ShowError(UIError(
                    errorString = t.message?:""
                )))
            }
        }
    }

    fun setProduct(product : Product) = viewModelScope.launch {
        _product.postValue(product)
        _colorsList.postValue(
            product.colors
                .map { ColorItem(it, false) }
                .toMutableList()
        )
        _imagesList.postValue(
            product.imageUrls.mapIndexed { index, s ->
                ImageItem(s, false)
            }.toMutableList()
        )
        viewModelScope.async {
            delay(200L)
            onImageItemClick(0)
        }
    }

    fun emptyState(){
        _state.postValue(ProductStates.Empty)
    }

    fun backOnClick() = viewModelScope.launch{
        _state.postValue(ProductStates.NavigateBack)
    }

    fun addTotal(amount : Int) = viewModelScope.launch {
        //_total.postValue((_total.value ?: 0f) + amount * (_product.value?.price ?: 0f))
        _total.postValue((_total.value ?: 0f) + amount)
    }

    fun onColorItemClick(position : Int) = viewModelScope.launch{
        _colorsList.value?.let{list->
            selectedColorPosition?.let{ selectedPosition->
                list[selectedPosition] = list[selectedPosition].copy(isSelected = false)
            }
            selectedColorPosition = position
            list[position] = list[position].copy(isSelected = true)
            _colorsList.postValue(list)
        }
    }

    fun onImageItemClick(position : Int) = viewModelScope.launch{
        _imagesList.value?.let{list->
            selectedImagePosition?.let{ selectedPosition->
                list[selectedPosition] = list[selectedPosition].copy(isSelected = false)
            }
            selectedImagePosition = position
            list[position] = list[position].copy(isSelected = true)
            _imagesList.postValue(list)
            _selectedImage.postValue(list[position].imageUrl)
        }
    }
}

sealed class ProductStates {
    object Empty : ProductStates()
    object NavigateBack : ProductStates()
    data class ShowError(val error: UIError) : ProductStates()
}