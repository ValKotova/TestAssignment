package com.valkotova.data

import android.database.sqlite.SQLiteConstraintException
import com.valkotova.database.UserData
import com.valkotova.database.UsersDao
import com.valkotova.model.UsersRepo
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(
    private val usersDao: UsersDao
): UsersRepo {
    override suspend fun addUser(firstName: String, lastName : String, email : String) {
        with(Dispatchers.IO){
            try {
                val rowId = usersDao.addUser(
                    UserData(
                        firstName = firstName, lastName = lastName, email = email
                    )
                )
                if(rowId == -1L)
                    throw UsersRepo.UserIsAlreadyExists()
            }catch(e: SQLiteConstraintException){
                throw UsersRepo.UserIsAlreadyExists()
            }catch (e: Exception){
                throw e
            }
        }
    }

    override suspend fun getUser(name: String) {
        with(Dispatchers.IO) {
            val result = usersDao.getUser(name)
            if(result.isEmpty())
                throw UsersRepo.UserNotFound()
            else
                return
        }
    }

}