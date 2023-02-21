package com.example.pizza_singh_capstone_project.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.repositories.CartRepository

class CartViewModel:ViewModel() {
    fun insert(context: Context,cartModel:CartModel)
    {
        CartRepository.insert(context,cartModel)
    }

    fun getAllCartData(context: Context): LiveData<List<CartModel>>
    {
        return CartRepository.getAllCartData(context)
    }
}