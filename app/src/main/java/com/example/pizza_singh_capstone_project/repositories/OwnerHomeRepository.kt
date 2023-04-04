package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pizza_singh_capstone_project.models.*
import com.example.pizza_singh_capstone_project.utils.Coroutines
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.Objects
import javax.inject.Inject

class OwnerHomeRepository @Inject constructor() {

    private val TAG: String? = "OwnerHomeRepository"
    private var firestore = FirebaseFirestore.getInstance()

    suspend fun getOrders(): ArrayList<OwnerOrderModel> {
        val list: ArrayList<OwnerOrderModel> = ArrayList()
        firestore.clearPersistence()
        CoroutineScope(Dispatchers.Main).async {
            firestore.collection("Orders").addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }
                list.clear()
                try {
                    val document = snapshot!!.toObjects(OwnerOrderModel::class.java)

                    for (i in document) {
                        val orderHistoryModel: OwnerOrderModel = OwnerOrderModel()
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
            delay(2000)
        }.await()
        return list
    }


}