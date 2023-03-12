package com.valkotova.testassignment.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valkotova.model.FlashSale
import com.valkotova.model.FlashSaleList
import com.valkotova.model.LatestList
import com.valkotova.model.ProductsRepo
import com.valkotova.presenter.R
import com.valkotova.presenter.ext.UIError
import com.valkotova.testassignment.ui.home.items.CategoryItem
import com.valkotova.testassignment.ui.home.items.FlashSaleItem
import com.valkotova.testassignment.ui.home.items.LatestItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val productsRepo : ProductsRepo
) : ViewModel(
) {
    private val _state : MutableLiveData<HomeStates> = MutableLiveData(HomeStates.Empty)
    val state : LiveData<HomeStates>
        get() = _state

    data class Lists(
        val latest : List<LatestItem>,
        val flashSale : List<FlashSaleItem>
    )

    private val _lists : MutableLiveData<Lists?> = MutableLiveData(null)
    val lists : LiveData<Lists?>
        get() = _lists

    private val _suggestions : MutableLiveData<List<String>> = MutableLiveData(listOf())
    val suggestions : LiveData<List<String>>
        get() = _suggestions
    private var words : List<String> = listOf()
    private var _search : String = ""

    init{
        val getLatest = viewModelScope.async {
            productsRepo.getLatest()
        }
        val getFlashSale = viewModelScope.async {
            productsRepo.getFlashSale()
        }
        viewModelScope.launch {
            try {
                viewModelScope.async(Dispatchers.IO) {
                    words = productsRepo.getListOfWords().words
                }.start()
                getLatest.start()
                getFlashSale.start()
                val latestData : LatestList = getLatest.await()
                val flashSaleData : FlashSaleList = getFlashSale.await()
                _lists.postValue(Lists(
                    latest = latestData.latest.map { LatestItem(it) },
                    flashSale = flashSaleData.flashSale.map { FlashSaleItem(it) }
                ))
            } catch(t: Throwable){
                _state.postValue(
                    HomeStates.ShowError(
                        UIError(
                            errorString = t.message ?: ""
                        )
                    ))
            }
        }
    }

    fun setSearchText(text: String, manually : Boolean = false) = viewModelScope.launch {
        if(text != _search){
            _search = text
            if(_search == "" || manually)
                notShowSuggestion()
            else
                _suggestions.postValue(words.filter { it.lowercase().contains(_search.lowercase()) })
        }

    }
    fun notShowSuggestion() = viewModelScope.launch {
        _suggestions.postValue(listOf())
    }

    fun initCategory() : List<CategoryItem> = listOf(
        CategoryItem("Phones", R.drawable.ic_phones),
        CategoryItem("Headphones", R.drawable.ic_headphones),
        CategoryItem("Games", R.drawable.ic_games),
        CategoryItem("Cars", R.drawable.ic_cars),
        CategoryItem("Furniture", R.drawable.ic_furniture),
        CategoryItem("Kids", R.drawable.ic_kids),
    )

    fun onFlashSaleClick(item : FlashSaleItem) = viewModelScope.launch {
        _state.postValue(HomeStates.NavigateToProduct(item.value))
    }

    fun emptyState() = viewModelScope.launch {
        _state.postValue(HomeStates.Empty)
    }

    fun backOnClick() = viewModelScope.launch {
        _state.postValue(HomeStates.NavigateBack)
    }

}

sealed class HomeStates {
    object Empty : HomeStates()
    object NavigateBack : HomeStates()
    data class NavigateToProduct(val item : FlashSale) : HomeStates()
    data class ShowError(val error: UIError) : HomeStates()
}