package com.ilhomsoliev.productstest.domain.model

import android.net.Uri
import com.google.gson.Gson

data class Product(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
){
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}