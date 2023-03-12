package com.valkotova.data

import com.valkotova.model.ProductsRepo
import com.valkotova.model.UsersRepo
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindProductsRepo(repoImpl: ProductsRepoImpl): ProductsRepo

    @Binds
    fun bindUsersRepo(repoImpl: UsersRepoImpl): UsersRepo
}