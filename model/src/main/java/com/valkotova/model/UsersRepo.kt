package com.valkotova.model

interface UsersRepo {
    suspend fun addUser(firstName: String, lastName : String, email : String)

    suspend fun getUser(name: String)

    class UserIsAlreadyExists: Throwable()
    class UserNotFound: Throwable()
}