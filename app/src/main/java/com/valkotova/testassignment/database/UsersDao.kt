package com.valkotova.testassignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface UsersDao  {

    @Query("SELECT * FROM users WHERE first_name = :name")
    suspend fun getUser(name: String) : List<UserData>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: UserData) : Long

}
