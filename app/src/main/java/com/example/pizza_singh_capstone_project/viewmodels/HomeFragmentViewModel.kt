package com.example.pizza_singh_capstone_project.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.BannersModel
import com.example.pizza_singh_capstone_project.models.CategoriesModel
import com.example.pizza_singh_capstone_project.repositories.HomeRepository
import com.example.pizza_singh_capstone_project.utils.Coroutines
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    var homeBanners: MutableLiveData<NetworkResult<ArrayList<BannersModel>>> = MutableLiveData()
    var categoriesList: MutableLiveData<NetworkResult<ArrayList<CategoriesModel>>> =
        MutableLiveData()

    fun getBanners() {
        Coroutines.main {
            if (homeRepository.getBannerImages().size > 0) {
                homeBanners.value = NetworkResult.Success(homeRepository.getBannerImages())
            }
        }
    }

    fun getCategories() {
        categoriesList.value = NetworkResult.Loading()
        Coroutines.main {
            val data = homeRepository.getCategories()
            if (data.size > 0) {
                categoriesList.value = NetworkResult.Success(data)
                NetworkResult.Success(categoriesList.value)
            } else {
                categoriesList.value = NetworkResult.Error("No Data Found!")
            }
        }
    }

}