package com.ilhomsoliev.productstest.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.feature.home.vm.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    vm: HomeScreenViewModel = koinViewModel(),
    openDetailsScreen: (id: Product) -> Unit,
    openSearchScreen: () -> Unit
) {
    val productPagingItems: LazyPagingItems<Product> = vm.productsState.collectAsLazyPagingItems()
    val categorise by vm.categories.collectAsState()
    val selectedCategory by vm.selectedCategory.collectAsState()

    HomeContent(
        state = HomeState(
            productPagingItems = productPagingItems,
            categorise = categorise,
            selectedCategory = selectedCategory
        ),
        callback = object : HomeCallback {
            override fun onClick(product: Product) {
                openDetailsScreen(product)
            }

            override fun onSearchClick() {
                openSearchScreen()
            }

            override fun onCategoryClick(value: String) {
                vm.changeCategory(value)
            }
        }
    )
}