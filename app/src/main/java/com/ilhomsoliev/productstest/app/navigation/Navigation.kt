package com.ilhomsoliev.productstest.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilhomsoliev.productstest.feature.detail.presentation.DetailScreen
import com.ilhomsoliev.productstest.feature.home.presentation.HomeScreen
import com.ilhomsoliev.productstest.feature.search.presentation.SearchScreen
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
            val profile = Screens.Detail.getProduct(it)
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