package com.valkotova.testassignment.ui.home

import androidx.lifecycle.*
import com.valkotova.testassignment.R
import com.valkotova.testassignment.model.FlashSaleListData
import com.valkotova.testassignment.model.LatestListData
import com.valkotova.testassignment.model.repository.ProductsRepo
import com.valkotova.testassignment.ui.home.items.CategoryItem
import com.valkotova.testassignment.ui.home.items.FlashSaleItem
import com.valkotova.testassignment.ui.home.items.LatestItem
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val productsRepo : ProductsRepo
) : ViewModel(
) {
    private val _latest : MutableLiveData<List<LatestItem>> = MutableLiveData(listOf())
    val latest : LiveData<List<LatestItem>>
        get() = _latest

    private val _flashSale : MutableLiveData<List<FlashSaleItem>> = MutableLiveData(listOf())
    val flashSale : LiveData<List<FlashSaleItem>>
        get() = _flashSale

    private val _suggestions : MutableLiveData<List<String>> = MutableLiveData(listOf())
    val suggestions : LiveData<List<String>>
        get() = _suggestions
    private var words : List<String> = listOf()
    private var _search : String = ""


    fun setSearchText(text: String, manually : Boolean = false){
        if(text != _search){
            _search = text
            if(_search == "" || manually)
                notShowSuggestion()
            else
                _suggestions.postValue(words.filter { it.lowercase().contains(_search.lowercase()) })
        }

    }
    fun notShowSuggestion(){
        _suggestions.postValue(listOf())
    }
    init{
        var latestData : LatestListData? = null
        var flashSaleData : FlashSaleListData? = null
        val getLatest = viewModelScope.async {
            latestData = productsRepo.getLatest()
        }
        val getFlashSale = viewModelScope.async {
            flashSaleData = productsRepo.getFlashSale()
        }
        viewModelScope.launch {
            try {
                viewModelScope.async(Dispatchers.IO) {
                    words = productsRepo.getListOfWords().words
                }.start()
                getLatest.start()
                getFlashSale.start()
                val isLatestComplete = getLatest.await()
                val isGetFlashSaleComplete = getFlashSale.await()
                _latest.postValue(latestData?.latest?.map { LatestItem(it) })
                _flashSale.postValue(flashSaleData?.flashSale?.map { FlashSaleItem(it) })
            } catch(t: Throwable){
                t.printStackTrace()
            }
        }
    }

    fun initCategory() : List<CategoryItem> = listOf(
        CategoryItem("Phones", R.drawable.ic_phones),
        CategoryItem("Headphones", R.drawable.ic_headphones),
        CategoryItem("Games", R.drawable.ic_games),
        CategoryItem("Cars", R.drawable.ic_cars),
        CategoryItem("Furniture", R.drawable.ic_furniture),
        CategoryItem("Kids", R.drawable.ic_kids),
    )
}