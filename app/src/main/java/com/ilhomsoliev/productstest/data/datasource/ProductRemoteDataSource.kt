package com.ilhomsoliev.productstest.data.datasource

import com.ilhomsoliev.productstest.data.model.network.dto.response.CategoriesResponseDto
import com.ilhomsoliev.productstest.data.model.network.dto.response.ProductsResponseDto


interface ProductRemoteDataSource {

    suspend fun getProducts(
        offset: Int,
        limit: Int,
        category: String,
    ): ProductsResponseDto?

    suspend fun getProductsByQuery(
        offset: Int,
        limit: Int,
        query: String,
    ): ProductsResponseDto?

    suspend fun getCategories(): CategoriesResponseDto?

}
