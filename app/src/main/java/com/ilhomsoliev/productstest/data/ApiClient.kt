package com.ilhomsoliev.productstest.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.ilhomsoliev.productstest.data.model.network.dto.response.ProductsResponseDto
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
        query: String = ""
    ): ProductsResponseDto? {
        val queryPart = (if (query != "") "&q=$query" else "")
        httpClient.get("https://dummyjson.com/products?skip=$offset&limit=$limit" + queryPart)
            .let { response ->
                try {
                    return response.body<ProductsResponseDto>()
                } catch (e: Exception) {
                    return null
                }
            }
    }
}