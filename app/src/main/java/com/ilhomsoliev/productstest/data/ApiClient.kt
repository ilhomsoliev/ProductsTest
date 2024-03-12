package com.ilhomsoliev.productstest.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.ilhomsoliev.productstest.data.model.network.dto.response.CategoriesResponseDto
import com.ilhomsoliev.productstest.data.model.network.dto.response.ProductsResponseDto
import com.ilhomsoliev.productstest.feature.home.vm.HomeScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.jackson.jackson

class ApiClient {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            jackson {
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                setSerializationInclusion(JsonInclude.Include.NON_NULL)
            }
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    suspend fun getProducts(
        offset: Int,
        limit: Int,
        category: String,
    ): ProductsResponseDto? {

        val url =
            if (category == HomeScreenViewModel.ALL_CATEGORY) "https://dummyjson.com/products?skip=$offset&limit=$limit"
            else "https://dummyjson.com/products/category/$category?skip=$offset&limit=$limit"
        httpClient.get(url)
            .let { response ->
                try {
                    return response.body<ProductsResponseDto>()
                } catch (e: Exception) {
                    return null
                }
            }
    }

    suspend fun getProductsByQuery(
        offset: Int,
        limit: Int,
        query: String = ""
    ): ProductsResponseDto? {
        val url = "https://dummyjson.com/products/search?q=$query&skip=$offset&limit=$limit"
        httpClient.get(url)
            .let { response ->
                try {
                    return response.body<ProductsResponseDto>()
                } catch (e: Exception) {
                    return null
                }
            }
    }

    suspend fun getCategories(): CategoriesResponseDto? {
        val url = "https://dummyjson.com/products/categories"
        httpClient.get(url)
            .let { response ->
                try {
                    return response.body<CategoriesResponseDto>()
                } catch (e: Exception) {
                    return null
                }
            }
    }
}