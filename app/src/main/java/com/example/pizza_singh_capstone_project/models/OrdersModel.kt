package com.example.pizza_singh_capstone_project.models

enum class ORDER_STATUS {
    accept,
    reject,
}

data class OrdersModel(
    var orderId: Long,
    var orderStatus: ORDER_STATUS? = null,
    var orderList: List<CartModel>,
    var userId: Long,
    var totalAmount: String
)
