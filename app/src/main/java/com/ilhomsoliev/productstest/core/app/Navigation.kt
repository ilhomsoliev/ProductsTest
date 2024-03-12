package com.ilhomsoliev.productstest.core.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.ilhomsoliev.productstest.core.app.navigation.Screens
import com.ilhomsoliev.productstest.domain.model.Product
import com.ilhomsoliev.productstest.presentation.detail.DetailScreen
import com.ilhomsoliev.productstest.presentation.home.HomeScreen
import com.ilhomsoliev.productstest.presentation.search.SearchScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation() {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(Screens.Home.route) {
            HomeScreen(openDetailsScreen = {
                navController.navigate(Screens.Detail.buildRoute(it))
            }, openSearchScreen = {
                navController.navigate(Screens.Search.route)
            })
        }

        composable(
            route = Screens.Detail.route
        ) {
            val profile =
                it.arguments?.getString("product")?.let { Gson().fromJson(it, Product::class.java) }
            DetailScreen(profile) {
                navController.popBackStack()
            }
        }
        composable(route = Screens.Search.route) {
            SearchScreen(koinViewModel(), onBack = {
                navController.popBackStack()
            }, openDetailsScreen = {
                navController.navigate(Screens.Detail.buildRoute(it))
            })
        }
    }
}