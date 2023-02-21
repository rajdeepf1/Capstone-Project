package com.example.pizza_singh_capstone_project.models

data class ProductModel(
    val productDescription: ArrayList<String>,
    val productImage: String,
    val productName: String,
    val productPrice: String,
    val productId: String,
    val isVeg: Boolean
)
