package com.example.pizza_singh_capstone_project.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.pizza_singh_capstone_project.database.AppDatabase
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.utils.Coroutines

class CartRepository {
    companion object{
        var appDatabase:AppDatabase?=null

        private fun intialiseDB(context: Context): AppDatabase?
        {
            return AppDatabase.getInstance(context)!!
        }

        fun insert(context: Context,cartData:CartModel)
        {
            appDatabase= intialiseDB(context)

//            CoroutineScope(IO).launch { //For IO operations
//            }

            Coroutines.main {
                appDatabase!!.cartDao().insert(cartData)
            }

        }

        fun getAllCartData(context: Context): LiveData<List<CartModel>>
        {
            appDatabase= intialiseDB(context)
            return appDatabase!!.cartDao().getAllCartData()
        }
    }
}