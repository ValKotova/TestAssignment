package com.valkotova.testassignment.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valkotova.presenter.R
import com.valkotova.model.UsersRepo
import com.valkotova.presenter.ext.UIError
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val usersRepo : UsersRepo
) : ViewModel(){
    private val _state : MutableLiveData<LogInStates> = MutableLiveData(LogInStates.Empty)
    val state : LiveData<LogInStates>
        get() = _state

    private val _firstName : MutableLiveData<String> = MutableLiveData("")
    val firstName : LiveData<String>
        get() = _firstName

    private val _password : MutableLiveData<String> = MutableLiveData("")
    val password : LiveData<String>
        get() = _password

    private val _isPasswordVisible : MutableLiveData<Boolean> = MutableLiveData(false)
    val isPasswordVisible : LiveData<Boolean>
        get() = _isPasswordVisible

    private val _firstNameIsInvalid : MutableLiveData<Boolean> = MutableLiveData(false)
    val firstNameIsInvalid : LiveData<Boolean>
        get() = _firstNameIsInvalid

    fun setFirstName(text: String){
        _firstName.postValue(text)
        _firstNameIsInvalid.postValue(false)
    }
    fun setPassword(text: String){
        _password.postValue(text)
    }

    fun changePasswordVisibility(){
        _isPasswordVisible.postValue(!(_isPasswordVisible.value?:true))
    }

    fun onLogIn() = viewModelScope.launch{
        _firstName.value?.let{ firstName->
            if(firstName.isEmpty())
                _firstNameIsInvalid.postValue(true)
            else{
                try{
                    usersRepo.getUser(firstName)
                    _state.postValue(LogInStates.NavigateToHome)
                } catch (t: UsersRepo.UserNotFound){
                    _state.postValue(
                        LogInStates.ShowError(
                            UIError(
                                errorId = R.string.error_user_not_found,
                                args = listOf(firstName)
                            )
                        )
                    )
                } catch (t : Throwable){
                    t.message?.let{
                        _state.postValue(
                            LogInStates.ShowError(
                                UIError(
                                    errorString = it
                                )
                            )
                        )
                    } ?: run {
                        _state.postValue(LogInStates.ShowError(UIError(R.string.error_unknown)))
                    }
                }
            }
        }
    }

    fun emptyState(){
        _state.postValue(LogInStates.Empty)
    }

}
sealed class LogInStates {
    object Empty : LogInStates()
    object NavigateToHome : LogInStates()
    data class ShowError(val error: UIError) : LogInStates()
}