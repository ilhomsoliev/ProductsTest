package com.ilhomsoliev.productstest.presentation.home

import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilhomsoliev.productstest.domain.model.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    vm: HomeScreenViewModel = koinViewModel(),
    openDetailsScreen: (id: Product) -> Unit,
    openSearchScreen: () -> Unit
) {
    val productPagingItems: LazyPagingItems<Product> = vm.productsState.collectAsLazyPagingItems()

    HomeContent(
        state = HomeState(productPagingItems),
        callback = object : HomeCallback {
            override fun onClick(product: Product) {
                openDetailsScreen(product)
            }

            override fun onSearchClick() {
                openSearchScreen()
            }
        }
    )
}