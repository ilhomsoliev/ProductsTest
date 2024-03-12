package com.ilhomsoliev.productstest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ilhomsoliev.productstest.data.datasource.ProductRemoteDataSource
import com.ilhomsoliev.productstest.data.repository.paging.ProductByQueryPagingSource
import com.ilhomsoliev.productstest.data.repository.paging.ProductPagingSource
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

const val NETWORK_PAGE_SIZE_LIMIT = 10
const val INITIAL_LOAD_SIZE_OFFSET = 0

class ProductRepositoryImpl constructor(
    private val remoteDataSource: ProductRemoteDataSource,
) : ProductRepository {

    override suspend fun getProducts(category: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE_LIMIT, prefetchDistance = 2),
            pagingSourceFactory = {
                ProductPagingSource(remoteDataSource = remoteDataSource, category = category)
            }
        ).flow
            .flowOn(Dispatchers.IO)

    }

    override suspend fun getProductsByQuery(query: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE_LIMIT, prefetchDistance = 2),
            pagingSourceFactory = {
                ProductByQueryPagingSource(remoteDataSource = remoteDataSource, query = query)
            }
        ).flow
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getCategories(): List<String> =
        remoteDataSource.getCategories()?.map { it } ?: listOf()

}