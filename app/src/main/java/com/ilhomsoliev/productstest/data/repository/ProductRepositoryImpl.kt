package com.ilhomsoliev.productstest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ilhomsoliev.productstest.data.datasource.MovieRemoteDataSource
import com.ilhomsoliev.productstest.data.repository.paging.MoviePagingSource
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ProductRepositoryImpl constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : ProductRepository {

    override suspend fun getProducts(query: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                MoviePagingSource(remoteDataSource)
            }
        ).flow
            .flowOn(Dispatchers.IO)

    }
}