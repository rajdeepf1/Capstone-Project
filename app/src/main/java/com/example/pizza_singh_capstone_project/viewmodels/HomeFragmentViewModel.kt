package com.example.pizza_singh_capstone_project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.BannersModel
import com.example.pizza_singh_capstone_project.repositories.HomeRepository
import com.example.pizza_singh_capstone_project.utils.Coroutines
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class HomeFragmentViewModel(val homeRepository: HomeRepository) : ViewModel() {

    var homeBanners: MutableLiveData<NetworkResult<ArrayList<BannersModel>>> = MutableLiveData()

    fun getBanners() {
        Coroutines.main {
            if (homeRepository.getBannerImages().size>0){
                homeBanners.value = NetworkResult.Success(homeRepository.getBannerImages())
            }
        }
    }

}