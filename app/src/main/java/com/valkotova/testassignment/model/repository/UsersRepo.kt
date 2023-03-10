package com.valkotova.testassignment.model.repository

import com.valkotova.testassignment.database.UserData

interface UsersRepo {
    suspend fun addUser(user: UserData)

    suspend fun getUser(name: String) : UserData

    class UserIsAlreadyExists: Throwable()
    class UserNotFound: Throwable()
}