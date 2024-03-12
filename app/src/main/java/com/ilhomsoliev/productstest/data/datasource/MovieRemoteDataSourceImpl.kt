package com.ilhomsoliev.productstest.data.datasource

import com.ilhomsoliev.productstest.data.ApiClient
import com.ilhomsoliev.productstest.data.model.network.dto.response.ProductsResponseDto

class MovieRemoteDataSourceImpl constructor(
    private val api: ApiClient
) : MovieRemoteDataSource {

    override suspend fun getProducts(
        offset: Int,
        limit: Int,
        query: String
    ): ProductsResponseDto? {
        return api.getProducts(
            offset = offset,
            limit = limit,
            query = query,
        )
    }

}