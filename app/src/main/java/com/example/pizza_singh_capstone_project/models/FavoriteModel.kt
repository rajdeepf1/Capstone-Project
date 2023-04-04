package com.example.pizza_singh_capstone_project.models

data class FavoriteModel(
    var favId: Long = 0,
    var userId: Long = 0,
    var favList: List<CartModel>? = null
)
