package com.example.pizza_singh_capstone_project.models

data class OrderDisplayModel(
    var orderId: Long = 0L,
    var orderStatus: ORDER_STATUS? = null,
    var userId: Long = 0L,
    var totalAmount: String = "",
    var productName: String ="",
    var productId: String="",
    var productPrice: String="",
    var productImage: String="",
    var isVeg: Boolean= false,
    var extraThings: String="",
    var qyt: Int=0
)