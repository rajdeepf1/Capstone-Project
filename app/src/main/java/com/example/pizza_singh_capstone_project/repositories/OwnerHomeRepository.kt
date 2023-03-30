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
                Log.d(TAG, "getOrders: called")
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }
                Log.d(TAG, "getOrders: called1")
                list.clear()
                snapshot!!.documents.map {
                    val orderId = it.data!!.getValue("orderId") as Long
                    val orderStatus = it.data!!.getValue("orderStatus") as ORDER_STATUS?
                    val userId = it.data!!.getValue("userId") as Long
                    val totalAmount = it.data!!.getValue("totalAmount") as String
                    val orderList: ArrayList<OwnerCartModel> =
                        it.data!!.getValue("orderList") as ArrayList<OwnerCartModel>
                    //Log.d(TAG, "getOrders: ${orderList}")
                    val data =
                        OwnerOrderModel(orderId, orderStatus, orderList, userId, totalAmount)
                    list.add(data)
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