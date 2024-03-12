package com.ilhomsoliev.productstest.data.datasource

import com.ilhomsoliev.productstest.data.ApiClient
import com.ilhomsoliev.productstest.data.model.network.dto.response.CategoriesResponseDto
import com.ilhomsoliev.productstest.data.model.network.dto.response.ProductsResponseDto

class ProductRemoteDataSourceImpl constructor(
    private val api: ApiClient
) : ProductRemoteDataSource {

    override suspend fun getProducts(
        offset: Int,
        limit: Int,
        category: String,
    ): ProductsResponseDto? {
        return api.getProducts(
            offset = offset,
            limit = limit,
            category = category
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

    override suspend fun getCategories(): CategoriesResponseDto? {
        return api.getCategories()
    }

}