package com.example.pizza_singh_capstone_project.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.GetFavoriteModel
import com.example.pizza_singh_capstone_project.repositories.FavroiteRepository
import com.example.pizza_singh_capstone_project.utils.Coroutines
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favroiteRepository: FavroiteRepository) : ViewModel() {

    private val TAG : String = "FavoriteViewModel"

    var favList: MutableLiveData<NetworkResult<ArrayList<GetFavoriteModel>>> = MutableLiveData()

    fun getFavorites(){
        favList.value = NetworkResult.Loading()
        Coroutines.main {
            val data = favroiteRepository.getFavorites()
            if (data.size >0){
                favList.value = NetworkResult.Success(data)
                NetworkResult.Success(favList.value)
            }else{
                favList.value = NetworkResult.Error("No Data Found!")
            }
        }
    }

}