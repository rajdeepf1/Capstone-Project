package com.example.pizza_singh_capstone_project.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.OrdersModel
import com.example.pizza_singh_capstone_project.models.OwnerOrderModel
import com.example.pizza_singh_capstone_project.repositories.OwnerHomeRepository
import com.example.pizza_singh_capstone_project.utils.Coroutines
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OwnerHomeFragmentViewModel @Inject constructor(private val ownerHomeRepository: OwnerHomeRepository) : ViewModel() {

    private val TAG : String = "OwnerHomeFragmentViewModel"

    var ordersList: MutableLiveData<NetworkResult<ArrayList<OwnerOrderModel>>> = MutableLiveData()

    fun getOrders(){
        ordersList.value = NetworkResult.Loading()
        Coroutines.main {
            val data = ownerHomeRepository.getOrders()
            if (data.size >0){
                ordersList.value = NetworkResult.Success(data)
                NetworkResult.Success(ordersList.value)
            }else{
                ordersList.value = NetworkResult.Error("No Data Found!")
            }
        }
    }

}