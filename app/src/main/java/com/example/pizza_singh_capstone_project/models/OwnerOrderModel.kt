package com.example.pizza_singh_capstone_project.models

data class OwnerOrderModel(
var orderId: Long,
var orderStatus: ORDER_STATUS? = null,
var orderList: List<OwnerCartModel>,
var userId: Long,
var totalAmount: String
)

