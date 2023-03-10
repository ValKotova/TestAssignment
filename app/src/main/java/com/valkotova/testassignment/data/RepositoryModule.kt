package com.valkotova.testassignment.data

import com.valkotova.testassignment.model.repository.ProductsRepo
import com.valkotova.testassignment.model.repository.UsersRepo
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindProductsRepo(repoImpl: ProductsRepoImpl): ProductsRepo
    @Binds
    fun bindUsersRepo(repoImpl: UsersRepoImpl): UsersRepo
}