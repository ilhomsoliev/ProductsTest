package com.ilhomsoliev.productstest.feature.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.domain.usecase.GetCategoriesUseCase
import com.ilhomsoliev.productstest.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {
    private val _productsState: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(value = PagingData.empty())
    val productsState: MutableStateFlow<PagingData<Product>> get() = _productsState

    private val _categories = MutableStateFlow<List<String>>(listOf(ALL_CATEGORY))
    val categories: StateFlow<List<String>> = _categories

    private val _selectedCategory = MutableStateFlow(ALL_CATEGORY)
    val selectedCategory: StateFlow<String> = _selectedCategory

    init {
        viewModelScope.launch {
            getCategories()
            getProducts()
        }
    }

    private suspend fun getProducts() {
        getProductsUseCase.execute(_selectedCategory.value)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _productsState.value = it
            }
    }

    private suspend fun getCategories() {
        val categories = getCategoriesUseCase.execute()
        _categories.value = _categories.value + categories
    }

    fun changeCategory(category: String) {
        viewModelScope.launch {
            _selectedCategory.value = category
            getProducts()
        }
    }

    companion object {
        const val ALL_CATEGORY = "All"
    }
}