package com.valkotova.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao  {

    @Query("SELECT * FROM users WHERE first_name = :name")
    suspend fun getUser(name: String) : List<UserData>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: UserData) : Long

}
