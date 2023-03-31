package com.example.pizza_singh_capstone_project.models

data class OwnerCartModel(
    val productName: String = "",
    val productId: String ="",
    val productPrice: String ="",
    val productImage: String ="",
    val isVeg: Boolean = false,
    val userId: String = "",
    val extraThings: String = "",
    var qyt: Int= 0
)
