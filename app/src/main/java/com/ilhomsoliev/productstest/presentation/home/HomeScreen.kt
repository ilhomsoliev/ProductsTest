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
    val moviePagingItems: LazyPagingItems<Product> = vm.moviesState.collectAsLazyPagingItems()

    HomeContent(
        state = HomeState(moviePagingItems),
        callback = object : HomeCallback {
            override fun onClick(id: Product) {
                openDetailsScreen(id)
            }

            override fun onSearchClick() {
                openSearchScreen()
            }
        }
    )
}