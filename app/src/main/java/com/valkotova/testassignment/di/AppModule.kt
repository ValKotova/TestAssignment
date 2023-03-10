package com.valkotova.testassignment.di

import android.content.Context
import com.valkotova.testassignment.App
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

    @Binds
    fun provideContext(app: App): Context
}