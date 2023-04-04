package com.example.pizza_singh_capstone_project.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// name of your table
@Entity(tableName = "cart")
data class CartModel(
    val productName: String,
    val productId: String,
    val productPrice: String,
    val productImage: String,
    val isVeg: Boolean,
    val userId: String,
    val extraThings: String,
    var qyt: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
