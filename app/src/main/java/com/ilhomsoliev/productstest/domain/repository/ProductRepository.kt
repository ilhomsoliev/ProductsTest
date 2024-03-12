package com.ilhomsoliev.productstest.domain.repository

import androidx.paging.PagingData
import com.ilhomsoliev.productstest.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(category: String): Flow<PagingData<Product>>
    suspend fun getProductsByQuery(query: String): Flow<PagingData<Product>>
    suspend fun getCategories(): List<String>
}