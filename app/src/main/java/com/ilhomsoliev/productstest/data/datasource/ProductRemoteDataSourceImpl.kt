package com.ilhomsoliev.productstest.data.datasource

import com.ilhomsoliev.productstest.data.ApiClient
import com.ilhomsoliev.productstest.data.model.network.dto.response.ProductsResponseDto

class ProductRemoteDataSourceImpl constructor(
    private val api: ApiClient
) : ProductRemoteDataSource {

    override suspend fun getProducts(
        offset: Int,
        limit: Int,
    ): ProductsResponseDto? {
        return api.getProducts(
            offset = offset,
            limit = limit,
        )
    }

    override suspend fun getProductsByQuery(
        offset: Int,
        limit: Int,
        query: String
    ): ProductsResponseDto? {
        return api.getProductsByQuery(
            offset = offset,
            limit = limit,
            query = query,
        )
    }

}