package com.ilhomsoliev.productstest.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _productsState: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(value = PagingData.empty())
    val productsState: MutableStateFlow<PagingData<Product>> get() = _productsState

    init {
        viewModelScope.launch {
            getProducts()
        }
    }

    private suspend fun getProducts() {
        getProductsUseCase.execute()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _productsState.value = it
            }
    }
}