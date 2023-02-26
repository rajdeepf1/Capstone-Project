package com.example.pizza_singh_capstone_project.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pizza_singh_capstone_project.models.CartModel

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartData:CartModel)

    @Query("SELECT * FROM cart where userId = :userId ORDER BY id ASC")
    fun getAllCartData(userId:Long): LiveData<List<CartModel>>

}