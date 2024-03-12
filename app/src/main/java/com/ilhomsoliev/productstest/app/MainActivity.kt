package com.ilhomsoliev.productstest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ilhomsoliev.productstest.app.navigation.Navigation
import com.ilhomsoliev.productstest.app.theme.ProductsTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductsTestTheme {
                Navigation()
            }
        }
    }
}