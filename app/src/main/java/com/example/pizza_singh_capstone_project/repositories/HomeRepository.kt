package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class HomeRepository {

    private val TAG: String? = "HomeRepository"
    val firestore = FirebaseFirestore.getInstance()

    suspend fun getBannerImages() : List<CarouselItem> {
        val list = mutableListOf<CarouselItem>()

        var documentReference:DocumentReference = firestore.collection("BannerImages").document()

        return userResponse
    }
}