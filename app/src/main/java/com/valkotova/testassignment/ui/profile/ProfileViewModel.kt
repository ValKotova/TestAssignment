package com.valkotova.testassignment.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.valkotova.testassignment.model.repository.ProductsRepo
import com.valkotova.testassignment.ui.ext.UIError
import com.valkotova.testassignment.ui.login.LogInStates
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
) : ViewModel(){
    private val _state : MutableLiveData<ProfileStates> = MutableLiveData(ProfileStates.Empty)
    val state : LiveData<ProfileStates>
        get() = _state

    private val _imageUrl : MutableLiveData<String?> = MutableLiveData(null)
    val imageUrl : LiveData<String?>
    get() = _imageUrl

    fun emptyState(){
        _state.postValue(ProfileStates.Empty)
    }

    fun logOut(){
        _state.postValue(ProfileStates.NavigateToSignIn)
    }

    fun setImageUrl(url : String){
        _imageUrl.postValue(url)
    }

    fun onChangeAvatar(){
        _state.postValue(ProfileStates.ChangeAvatar)
    }
}
sealed class ProfileStates {
    object Empty : ProfileStates()
    object NavigateToSignIn : ProfileStates()
    data class ShowError(val error: UIError) : ProfileStates()
    object ChangeAvatar : ProfileStates()
}