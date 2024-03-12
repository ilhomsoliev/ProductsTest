package com.ilhomsoliev.productstest.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilhomsoliev.productstest.domain.model.Product

@Composable
fun SearchScreen(
    vm: SearchScreenViewModel,
    onBack: () -> Unit,
    openDetailsScreen: (id: Product) -> Unit,
) {

    val query by vm.query.collectAsState()
    val productPagingItems: LazyPagingItems<Product> = vm.productsState.collectAsLazyPagingItems()

    SearchContent(
        state = SearchState(query = query, productPagingItems = productPagingItems),
        callback = object : SearchCallback {
            override fun onPromptChange(value: String) {
                vm.changePrompt(value)
            }

            override fun onBack() {
                onBack()
            }

            override fun onClick(product: Product) {
                openDetailsScreen(product)
            }
        }
    )
}