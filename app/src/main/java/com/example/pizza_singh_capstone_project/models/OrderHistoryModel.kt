package com.example.pizza_singh_capstone_project.models

data class OrderHistoryModel(
    var orderId: Long = 0,
    var orderStatus: String = "",
    var orderList: MutableList<OwnerCartModel>? = null,
    var userId: Long = 0,
    var totalAmount: String = "",
    var productName: String=""
)

