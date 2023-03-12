package com.valkotova.testassignment.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valkotova.presenter.ext.UIError
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
) : ViewModel(){
    private val _state : MutableLiveData<ProfileStates> = MutableLiveData(ProfileStates.Empty)
    val state : LiveData<ProfileStates>
        get() = _state

    private val _imageUrl : MutableLiveData<String?> = MutableLiveData(null)
    val imageUrl : LiveData<String?>
    get() = _imageUrl

    fun emptyState() = viewModelScope.launch{
        _state.postValue(ProfileStates.Empty)
    }

    fun logOut() = viewModelScope.launch{
        _state.postValue(ProfileStates.NavigateToSignIn)
    }

    fun setImageUrl(url : String) = viewModelScope.launch{
        _imageUrl.postValue(url)
    }

    fun onChangeAvatar() = viewModelScope.launch{
        _state.postValue(ProfileStates.ChangeAvatar)
    }

    fun backClicked() = viewModelScope.launch{
        _state.postValue(ProfileStates.NavigateBack)
    }
}
sealed class ProfileStates {
    object Empty : ProfileStates()
    object NavigateToSignIn : ProfileStates()
    object NavigateBack : ProfileStates()
    data class ShowError(val error: UIError) : ProfileStates()
    object ChangeAvatar : ProfileStates()
}