package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
import com.example.pizza_singh_capstone_project.models.ORDER_STATUS
import com.example.pizza_singh_capstone_project.models.OrderHistoryModel
import com.example.pizza_singh_capstone_project.models.OwnerCartModel
import com.example.pizza_singh_capstone_project.models.OwnerOrderModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import javax.inject.Inject

class OrderHistoryRepository @Inject constructor() {
    private val TAG: String? = "OrderHistoryRepository"
    private var firestore = FirebaseFirestore.getInstance()

    suspend fun getOrdersHistory(): ArrayList<OrderHistoryModel> {
        val list: ArrayList<OrderHistoryModel> = ArrayList()
        CoroutineScope(Dispatchers.Main).async {
            firestore.collection("OrderHistory").addSnapshotListener { snapshot, e ->
                Log.d(TAG, "getOrders: called")
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }
                Log.d(TAG, "getOrders: called1")
                list.clear()
//                snapshot!!.documents.map {
//                    try {
//
//                        val orderId = it.data!!.getValue("orderId") as Long
//
//                        val orderStatus = it.data!!.getValue("orderStatus") as String?
//                        val userId = it.data!!.getValue("userId") as Long
//                        val totalAmount = it.data!!.getValue("totalAmount") as String
//                        val orderList = it.data!!.get("orderList") as ArrayList<OwnerCartModel>
//                        val data =
//                            OrderHistoryModel(orderId, orderStatus!!, orderList, userId, totalAmount)
//                        list.add(data)
//                    } catch (e: Exception) {
//                        Log.d(TAG, "getOrdersHistory: ${e.message}")
//                    }
//                }
                try {
                    val document = snapshot!!.toObjects(OrderHistoryModel::class.java)

                    for (i in document) {
                        val orderHistoryModel: OrderHistoryModel = OrderHistoryModel()
                        orderHistoryModel.orderId = i.orderId
                        orderHistoryModel.orderStatus = i.orderStatus
                        orderHistoryModel.userId = i.userId
                        orderHistoryModel.totalAmount = i.totalAmount
                        orderHistoryModel.orderList = i.orderList!!.toMutableList()
                        list.add(orderHistoryModel)
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "getOrdersHistory: ${e.message}")
                }

            }
            Log.d(TAG, "getOrders: called3")
            delay(2000)
            Log.d(TAG, "getOrders: called4")
        }.await()
        Log.d(TAG, "getOrders: ${list.size}")
        return list!!
    }
}