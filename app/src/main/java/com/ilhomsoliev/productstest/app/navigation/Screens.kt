package com.ilhomsoliev.productstest.app.navigation

import androidx.navigation.NavBackStackEntry
import com.google.gson.Gson
import com.ilhomsoliev.productstest.domain.model.Product

sealed class Screens(val route: String) {

    data object Home : Screens("Home")
    data object Search : Screens("Search")

    data object Detail : Screens("Detail/{$DETAIL_ARG_KEY}") {

        fun buildRoute(product: Product): String = "Detail/${product.toJson()}"

        fun getProduct(entry: NavBackStackEntry): Product? =
            entry.arguments?.getString(DETAIL_ARG_KEY)
                ?.let { Gson().fromJson(it, Product::class.java) }
    }

    companion object {
        const val DETAIL_ARG_KEY = "product"
    }
}