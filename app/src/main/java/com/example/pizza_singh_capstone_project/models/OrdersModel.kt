package com.example.pizza_singh_capstone_project.models

enum class ORDER_STATUS {
    accept,
    reject,
}

data class OrdersModel(
    var orderId: Long = 0,
    var orderStatus: ORDER_STATUS? = null,
    var orderList: List<CartModel>? = null,
    var userId: Long = 0,
    var totalAmount: String = ""
)
