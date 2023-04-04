package com.example.pizza_singh_capstone_project.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.OrderHistoryModel
import com.example.pizza_singh_capstone_project.models.OwnerOrderModel
import com.example.pizza_singh_capstone_project.repositories.OrderHistoryRepository
import com.example.pizza_singh_capstone_project.repositories.ProductListRepository
import com.example.pizza_singh_capstone_project.utils.Coroutines
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(private val orderHistoryRepository: OrderHistoryRepository) :
    ViewModel() {
    private val TAG : String = "OrderHistoryViewModel"

    var ordersList: MutableLiveData<NetworkResult<ArrayList<OrderHistoryModel>>> = MutableLiveData()

    fun getOrders(){
        ordersList.value = NetworkResult.Loading()
        Coroutines.main {
            val data = orderHistoryRepository.getOrdersHistory()
            Log.d(TAG, "getOrdersHistory: ${data.toString()}")
            if (data != null && data!!.size >0){
                ordersList.value = NetworkResult.Success(data!!)
                NetworkResult.Success(ordersList.value)
            }else{
                ordersList.value = NetworkResult.Error("No Data Found!")
            }
        }
    }
}