package com.valkotova.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserData (
    @PrimaryKey @ColumnInfo(name = "first_name") val firstName : String,
    @ColumnInfo(name = "last_name") val lastName : String,
    @ColumnInfo(name = "email") val email : String,
)