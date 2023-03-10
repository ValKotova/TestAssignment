package com.valkotova.testassignment.di

import com.valkotova.testassignment.App
import com.valkotova.testassignment.data.RepositoryModule
import com.valkotova.testassignment.database.DaoModule
import com.valkotova.testassignment.di.viewModel.ViewModelModule
import com.valkotova.testassignment.network.NetworkModule
import com.valkotova.testassignment.ui.ProductFragment.ProductFragment
import com.valkotova.testassignment.ui.home.HomeFragment
import com.valkotova.testassignment.ui.login.LogInFragment
import com.valkotova.testassignment.ui.profile.ProfileFragment
import com.valkotova.testassignment.ui.signIn.SignInFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, NetworkModule::class, RepositoryModule::class, DaoModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun create(): AppComponent
    }

    fun inject(fragment : HomeFragment)
    fun inject(fragment : SignInFragment)
    fun inject(fragment : LogInFragment)
    fun inject(fragment : ProfileFragment)
    fun inject(fragment : ProductFragment)
}