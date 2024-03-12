package com.ilhomsoliev.productstest.feature.detail.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.productstest.R
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.feature.shared.SCachedImage

data class DetailState(
    val product: Product,
)

interface DetailCallback {
    fun onBack()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    state: DetailState,
    callback: DetailCallback
) {
    val product = state.product
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(product.title)
            }, navigationIcon = {
                IconButton(onClick = {
                    callback.onBack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SCachedImage(
                    url = product.thumbnail,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Crop
                )
            }
            item {
                Text(text = stringResource(R.string.price, product.price))
                Text(text = stringResource(R.string.rating, product.rating))
                Text(text = stringResource(R.string.brand, product.brand))
                Text(text = stringResource(R.string.category, product.category))
                Text(text = stringResource(R.string.stock, product.stock))
                Text(
                    text = stringResource(
                        R.string.discount_percentage,
                        product.discountPercentage
                    )
                )
            }
            item {
                Text(text = stringResource(R.string.images))
            }
            items(product.images) {
                SCachedImage(
                    url = it,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}