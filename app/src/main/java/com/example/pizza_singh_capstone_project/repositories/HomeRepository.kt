package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
import com.example.pizza_singh_capstone_project.models.BannersModel
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
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful){
                     it.result.toObjects(BannersModel::class.java).map {
                       list?.add(BannersModel(it.imgPath))
                    }
                }else{
                    list = null
                }

            }).addOnFailureListener(OnFailureListener {
                Log.d(TAG, "getUserData: login failure")
                list = null
            }).await()
        return list!!
    }
}