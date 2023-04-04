package com.example.pizza_singh_capstone_project.models

data class OwnerOrderModel(
var orderId: Long = 0,
var orderStatus: ORDER_STATUS? = null,
var orderList: MutableList<OwnerCartModel>? = null,
var userId: Long = 0,
var totalAmount: String = ""
)

