package com.ilhomsoliev.productstest.domain.usecase

import androidx.paging.PagingData
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase  (
    private val repository: ProductRepository
) {
    suspend fun execute(): List<String>{
        return repository.getCategories()
    }
}