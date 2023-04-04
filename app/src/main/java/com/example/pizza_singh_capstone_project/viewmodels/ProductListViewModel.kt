package com.example.pizza_singh_capstone_project.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.BannersModel
import com.example.pizza_singh_capstone_project.models.ProductModel
import com.example.pizza_singh_capstone_project.repositories.ProductListRepository
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.Coroutines
import com.example.pizza_singh_capstone_project.utils.InternetConnectionCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val productListRepository: ProductListRepository):ViewModel() {

    private val TAG: String = "ProductListViewModel"

    var productList: MutableLiveData<NetworkResult<List<ProductModel>>> = MutableLiveData()

    fun getProductList(context: Context, category_name: String,category_id: String){
        if (InternetConnectionCheck.isOnline(context)){
            Coroutines.main {
                productList.value = NetworkResult.Loading()
                val response = productListRepository.getProducts(category_name,category_id)
                if (!response.isEmpty()){
                    productList.value = NetworkResult.Success(response)
                }else{
                    productList.value = NetworkResult.Error("No Data Found")
                }

            }
        }else{
            productList.value = NetworkResult.Error("No Internet Connection!")
        }
    }

}