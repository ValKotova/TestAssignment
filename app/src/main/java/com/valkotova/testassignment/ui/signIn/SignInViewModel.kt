package com.valkotova.testassignment.ui.signIn

import android.util.Patterns
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.valkotova.testassignment.R
import com.valkotova.testassignment.database.UserData
import com.valkotova.testassignment.model.repository.ProductsRepo
import com.valkotova.testassignment.model.repository.UsersRepo
import com.valkotova.testassignment.ui.ext.UIError
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val usersRepo : UsersRepo
) : ViewModel(){
    private val _state : MutableLiveData<SignInState> = MutableLiveData(SignInState.Empty)
    val state : LiveData<SignInState>
        get() = _state

    private val _email : MutableLiveData<String> = MutableLiveData("")
    val email : LiveData<String>
        get() = _email

    private val _firstName : MutableLiveData<String> = MutableLiveData("")
    val firstName : LiveData<String>
        get() = _firstName

    private val _lastName : MutableLiveData<String> = MutableLiveData("")
    val lastName : LiveData<String>
        get() = _lastName

    private val _emailIsInvalid : MutableLiveData<Boolean> = MutableLiveData(false)
    val emailIsInvalid : LiveData<Boolean>
        get() = _emailIsInvalid

    fun setEmail(text: String) = viewModelScope.launch {
        if(text != _email.value) {
            _email.postValue(text)
            if(!Patterns.EMAIL_ADDRESS.matcher(text).matches())
                _emailIsInvalid.postValue(true)
            else
                _emailIsInvalid.postValue(false)
        }
    }

    fun setFirstName(text: String){
        _firstName.postValue(text)
    }
    fun setLastName(text: String){
        _lastName.postValue(text)
    }

    fun emptyState(){
        _state.postValue(SignInState.Empty)
    }

    fun onSignIn() = viewModelScope.launch{
        _firstName.value?.let{ firstName ->
            if(firstName.isEmpty())
                _state.postValue(SignInState.ShowError(UIError(R.string.error_empty_first_name)))
            else{
                _email.value?.let { email ->
                    if (email.isEmpty()
                        || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    ) {
                        _state.postValue(SignInState.ShowError(UIError(R.string.error_invalid_email)))
                        _emailIsInvalid.postValue(true)
                    }
                    else {
                        addUser(UserData(
                            email = email,
                            firstName = firstName,
                            lastName = _lastName.value?:""))
                    }
                } ?: run {
                    _state.postValue(SignInState.ShowError(UIError(R.string.error_empty_email)))
                    _emailIsInvalid.postValue(true)
                }
            }
        } ?: run {
            _state.postValue(SignInState.ShowError(UIError(R.string.error_empty_first_name)))
        }
    }

    private suspend fun addUser(userData: UserData) {
        try{
            usersRepo.addUser(userData)
            _state.postValue(SignInState.NavigateToHome)
        } catch (t: UsersRepo.UserIsAlreadyExists){
            _state.postValue(SignInState.ShowError(UIError(errorId = R.string.error_user_already_exists, args = listOf(userData.firstName))))
        }
        catch(t : Throwable){
            t.message?.let{
                _state.postValue(SignInState.ShowError(UIError(errorString = it)))
            } ?: run {
                _state.postValue(SignInState.ShowError(UIError(R.string.error_unknown)))
            }
        }
    }

    fun onLogIn(){
        _state.postValue(SignInState.NavigateToLogIn)
    }
}

sealed class SignInState{
    object Empty : SignInState()
    object NavigateToHome : SignInState()
    object NavigateToLogIn : SignInState()
    data class ShowError(val error: UIError) : SignInState()
}