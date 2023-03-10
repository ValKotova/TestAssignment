package com.valkotova.testassignment

import android.app.Application
import android.content.Context
import com.valkotova.testassignment.di.AppComponent
import com.valkotova.testassignment.di.DaggerAppComponent

class App: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }