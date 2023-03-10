package com.valkotova.testassignment.database

import android.content.Context
import com.valkotova.testassignment.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DaoModule {
    @Provides
    @Singleton
    fun provideDao(context: Context) =
        AppDatabase.getInstance(context.applicationContext).usersDao()
}