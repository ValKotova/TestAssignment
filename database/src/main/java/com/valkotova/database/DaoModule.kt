package com.valkotova.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideDao(database: AppDatabase) =
        database.usersDao()
}