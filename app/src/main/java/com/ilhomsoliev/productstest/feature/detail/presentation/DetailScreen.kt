package com.ilhomsoliev.productstest.feature.detail.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ilhomsoliev.productstest.R
import com.ilhomsoliev.productstest.domain.model.Product

@Composable
fun DetailScreen(
    product: Product?,
    onBack: () -> Unit
) {
    product?.let {
        DetailContent(
            state = DetailState(it),
            callback = object : DetailCallback {
                override fun onBack() {
                    onBack()
                }
            }
        )
    }
    if (product == null) {
        Text(text = stringResource(R.string.error))
        Button(onClick = {
            onBack()
        }) {
            Text(text = stringResource(R.string.click_to_exit))
        }
    }
}