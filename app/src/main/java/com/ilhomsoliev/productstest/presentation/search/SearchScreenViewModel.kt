package com.ilhomsoliev.productstest.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.domain.usecase.GetProductsByQueryUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchScreenViewModel(
    private val getProductsByQueryUseCase: GetProductsByQueryUseCase
) : ViewModel() {
    private val _productsState: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(value = PagingData.empty())
    val productsState: StateFlow<PagingData<Product>> = _productsState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private var debounceJob: Job? = null

    init {
        viewModelScope.launch {
            getProducts()
        }
    }

    private suspend fun getProducts() {
        getProductsByQueryUseCase.execute(_query.value)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _productsState.value = it
            }
    }

    fun changePrompt(value: String) {
        viewModelScope.launch {
            _query.value = value
            debounceJob?.cancel()
            debounceJob = CoroutineScope(Dispatchers.Main).launch {
                delay(500)

                getProducts()
            }
        }
    }
}