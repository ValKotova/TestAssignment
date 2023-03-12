package com.valkotova.testassignment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valkotova.testassignment.di.viewModel.ViewModelFactory
import com.valkotova.testassignment.di.viewModel.ViewModelKey
import com.valkotova.testassignment.ui.product.ProductViewModel
import com.valkotova.testassignment.ui.home.HomeViewModel
import com.valkotova.testassignment.ui.login.LogInViewModel
import com.valkotova.testassignment.ui.profile.ProfileViewModel
import com.valkotova.testassignment.ui.signIn.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun provideSignUpViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    abstract fun provideLogInViewModel(viewModel: LogInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun provideProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun provideProductViewModel(viewModel: ProductViewModel): ViewModel

}