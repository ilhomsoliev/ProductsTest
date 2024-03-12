package com.ilhomsoliev.productstest.data.model.network.mapper

import com.ilhomsoliev.productstest.data.model.network.dto.response.ProductResponseDto
import com.ilhomsoliev.productstest.domain.model.Product

fun ProductResponseDto.mapFromEntity() = Product(
    brand = brand,
    category = category,
    description = description,
    discountPercentage = discountPercentage,
    id = id,
    images = images,
    price = price,
    rating = rating,
    stock = stock,
    thumbnail = thumbnail,
    title = title,
)

fun List<ProductResponseDto>.mapFromListModel(): List<Product> {
    return this.map {
        it.mapFromEntity()
    }
}
