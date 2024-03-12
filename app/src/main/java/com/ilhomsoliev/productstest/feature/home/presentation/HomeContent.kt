package com.ilhomsoliev.productstest.feature.home.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ilhomsoliev.productstest.R
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.feature.home.presentation.components.CustomSpinner
import com.ilhomsoliev.productstest.feature.home.presentation.components.ErrorMessage
import com.ilhomsoliev.productstest.feature.home.presentation.components.LoadingNextPageItem
import com.ilhomsoliev.productstest.feature.home.presentation.components.PageLoader
import com.ilhomsoliev.productstest.feature.home.presentation.components.ProductItem

data class HomeState(
    val productPagingItems: LazyPagingItems<Product>,
    val categorise: List<String>,
    val selectedCategory: String,
)

interface HomeCallback {
    fun onClick(product: Product)
    fun onSearchClick()
    fun onCategoryClick(value: String)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeState,
    callback: HomeCallback,
) {
    val productPagingItems: LazyPagingItems<Product> = state.productPagingItems

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(R.string.products))
            }, actions = {
                IconButton(onClick = {
                    callback.onSearchClick()
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            item { Spacer(modifier = Modifier.padding(4.dp)) }
            item {
                CustomSpinner(
                    options = state.categorise,
                    selectedCategory = state.selectedCategory,
                    onSelectCategory = {
                        callback.onCategoryClick(it)
                    }
                )
            }
            items(productPagingItems.itemCount) { index ->
                val item = productPagingItems[index]!!
                ProductItem(
                    modifier = Modifier.padding(vertical = 8.dp),
                    imageUrl = item.thumbnail,
                    title = item.title,
                    description = item.description,
                    onClick = {
                        callback.onClick(item)
                    }
                )
                Divider()
            }

            productPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = productPagingItems.loadState.refresh as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier.fillParentMaxSize(),
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = productPagingItems.loadState.append as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.padding(4.dp)) }

        }
    }
}