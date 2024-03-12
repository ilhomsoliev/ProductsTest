package com.ilhomsoliev.productstest.presentation.home

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
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.presentation.home.components.ErrorMessage
import com.ilhomsoliev.productstest.presentation.home.components.LoadingNextPageItem
import com.ilhomsoliev.productstest.presentation.home.components.PageLoader
import com.ilhomsoliev.productstest.presentation.home.components.ProductItem

data class HomeState(
    val moviePagingItems: LazyPagingItems<Product>
)

interface HomeCallback {
    fun onClick(id: Product)
    fun onSearchClick()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeState,
    callback: HomeCallback,
) {
    val moviePagingItems: LazyPagingItems<Product> = state.moviePagingItems

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Products")
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
            items(moviePagingItems.itemCount) { index ->
                val item = moviePagingItems[index]!!
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

            moviePagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = moviePagingItems.loadState.refresh as LoadState.Error
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
                        val error = moviePagingItems.loadState.append as LoadState.Error
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