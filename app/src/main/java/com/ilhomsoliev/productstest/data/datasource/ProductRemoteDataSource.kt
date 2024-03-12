package com.ilhomsoliev.productstest.data.datasource

import com.ilhomsoliev.productstest.data.model.network.dto.response.ProductsResponseDto


interface ProductRemoteDataSource {

    suspend fun getProducts(
        offset: Int,
        limit: Int,
    ): ProductsResponseDto?

    suspend fun getProductsByQuery(
        offset: Int,
        limit: Int,
        query: String,
    ): ProductsResponseDto?

}
