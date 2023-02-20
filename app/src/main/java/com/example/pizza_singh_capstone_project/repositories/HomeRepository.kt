package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.BannersModel
import com.example.pizza_singh_capstone_project.models.CategoriesModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeRepository {

    private val TAG: String? = "HomeRepository"
    val firestore = FirebaseFirestore.getInstance()

    suspend fun getBannerImages():ArrayList<BannersModel> {
        var list: ArrayList<BannersModel>? = ArrayList<BannersModel>()
        firestore.collection("BannerImages")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.toObjects(BannersModel::class.java).map {
                        list?.add(BannersModel(it.imgPath))
                    }
                } else {
                    list = null
                }

            }.addOnFailureListener {
                Log.d(TAG, "getUserData: login failure")
                list = null
            }.await()
        return list!!
    }

    suspend fun getCategories(): ArrayList<CategoriesModel> {
        var networkResult: ArrayList<CategoriesModel>? = ArrayList()
        firestore.collection("Categories").get().addOnCompleteListener{
            if (it.isSuccessful){


                it.result.documents.map {
                    val id: String = it.id
                    val categoryName: String = it.data?.getValue("categoryName").toString()
                    val categoryImage:String = it.data?.getValue("categoryImage").toString()
                    networkResult!!.add(CategoriesModel(id = id, categoryName = categoryName, categoryImage = categoryImage))
                }

                //networkResult = it.result.toObjects(CategoriesModel::class.java) as ArrayList<CategoriesModel>

            }else{
                NetworkResult.Error("No Data Found!",null)
            }
        }.addOnFailureListener{
            NetworkResult.Error(it.message.toString(),null)
        }.await()
        return  networkResult!!
    }

}