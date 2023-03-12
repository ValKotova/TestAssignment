package com.valkotova.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.valkotova.database.BuildConfig.DATABASE_NAME

@Database(entities = [UserData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}
