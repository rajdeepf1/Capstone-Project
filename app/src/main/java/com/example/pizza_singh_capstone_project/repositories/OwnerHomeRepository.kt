package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.models.ORDER_STATUS
import com.example.pizza_singh_capstone_project.models.OrderDisplayModel
import com.example.pizza_singh_capstone_project.models.OrdersModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.util.logging.Handler
import javax.inject.Inject

class OwnerHomeRepository @Inject constructor() {

    private val TAG: String? = "OwnerHomeRepository"
    val firestore = FirebaseFirestore.getInstance()

//    suspend fun getOrders(): ArrayList<OrdersModel> {
//        var result: ArrayList<OrdersModel>? = ArrayList()
//        firestore.collection("Orders").get().addOnCompleteListener {
//            if (it.isSuccessful) {
//
//                it.result.documents.map {
//                    val data = OrdersModel(
//                        it.get("orderId") as Long,
//                        it.get("orderStatus") as ORDER_STATUS?,
//                        it.get("orderList") as List<CartModel>,
//                        it.get("userId") as Long,
//                        it.get("totalAmount") as String
//                    )
//
//                    Log.d(TAG, "getOrders: ${data}")
//
//                    result?.add(data)
//
//                }
//
//                NetworkResult.Success(result)
//
//            } else {
//                 NetworkResult.Error("No Data Found!",null)
//            }
//        }.addOnFailureListener {
//            NetworkResult.Error(it.message.toString(), null)
//        }.await()
//        return  result!!
//    }

    suspend fun getOrders(): ArrayList<OrdersModel> {
        val result: ArrayList<OrdersModel> = ArrayList<OrdersModel>()
        firestore.collection("Orders").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "listen:error", e)
                return@addSnapshotListener
            }
            snapshot!!.documents.map {
                val data = OrdersModel(
                    it.get("orderId") as Long,
                    it.get("orderStatus") as ORDER_STATUS?,
                    it.get("orderList") as List<CartModel>,
                    it.get("userId") as Long,
                    it.get("totalAmount") as String
                )
                result.add(data)

//                try {
//                    val data = OrderDisplayModel()
//                    data.orderId = it.get("orderId") as Long
//                    data.orderStatus = it.get("orderStatus") as ORDER_STATUS?
//                    data.userId = it.get("userId") as Long
//                    data.totalAmount = it.get("totalAmount") as String
//                    (it.get("orderList")!! as ArrayList<CartModel>).map {
//                        data.productName = it.productName as String
//                        data.productId= it.productId
//                        data.productPrice = it.productPrice
//                        data.productImage = it.productImage
//                        data.isVeg= it.isVeg
//                        data.extraThings  = it.extraThings
//                        data.qyt = it.qyt
//                    }
//                    result.add(data)
//
//                }catch (e:Exception){
//                    Log.d(TAG, "getOrders: ${e.message}")
//                }



                //Log.d(TAG, "getOrders: ${data}")

            }
        }
        delay(2000L)
        return result
    }


}