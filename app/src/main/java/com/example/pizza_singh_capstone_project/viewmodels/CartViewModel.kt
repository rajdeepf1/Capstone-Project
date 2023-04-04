package com.example.pizza_singh_capstone_project.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.models.FeedbackModel
import com.example.pizza_singh_capstone_project.repositories.CartRepository
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.Coroutines
import java.util.*

class CartViewModel:ViewModel() {
    fun insert(context: Context,cartModel:CartModel)
    {
        CartRepository.insert(context,cartModel)
    }

    fun getAllCartData(context: Context,userId:Long): LiveData<List<CartModel>>
    {
        return CartRepository.getAllCartData(context, userId = userId)
    }

    fun deleteAllCartData(context: Context)
    {
        CartRepository.deleteAllCartData(context = context)
    }

    fun deleteCartDataById(context: Context,cartItemId:Int)
    {
        CartRepository.deleteCartDataById(context = context,cartItemId= cartItemId)
    }

}