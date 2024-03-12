package com.ilhomsoliev.productstest.app.navigation

import androidx.navigation.NavBackStackEntry
import com.ilhomsoliev.productstest.domain.model.Product

sealed class Screens(val route: String) {

    data object Home : Screens("Home")
    data object Search : Screens("Search")

    data object Detail : Screens("Detail/{product}") {

        fun buildRoute(product: Product): String = "Detail/${product.toJson()}"

        fun getId(entry: NavBackStackEntry): String? = entry.arguments?.getString("id")
    }
}