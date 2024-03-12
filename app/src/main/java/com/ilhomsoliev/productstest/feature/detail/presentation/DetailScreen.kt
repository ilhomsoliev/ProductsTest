package com.ilhomsoliev.productstest.feature.detail.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
        Text(text = "Error")
        Button(onClick = {
            onBack()
        }) {
            Text(text = "Click to exit")
        }
    }
}