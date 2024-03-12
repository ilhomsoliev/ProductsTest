package com.ilhomsoliev.productstest.data.model.network.dto.response

data class ProductsResponseDto(
    val limit: Int,
    val products: List<ProductResponseDto>,
    val skip: Int,
    val total: Int
)