package com.ilhomsoliev.productstest.data.datasource

import com.ilhomsoliev.productstest.data.model.network.dto.response.ProductsResponseDto


interface MovieRemoteDataSource {

    suspend fun getProducts(
        offset: Int,
        limit: Int,
        query: String = ""
    ): ProductsResponseDto?

}
