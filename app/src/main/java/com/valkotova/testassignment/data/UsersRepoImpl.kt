package com.valkotova.testassignment.data

import android.database.sqlite.SQLiteConstraintException
import com.valkotova.testassignment.database.UserData
import com.valkotova.testassignment.database.UsersDao
import com.valkotova.testassignment.model.repository.UsersRepo
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(
    private val usersDao: UsersDao
): UsersRepo {
    override suspend fun addUser(user: UserData) {
        with(Dispatchers.IO){
            try {
                val rowId = usersDao.addUser(user)
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