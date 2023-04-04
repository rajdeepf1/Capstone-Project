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

class FavroiteRepository @Inject constructor() {

    private val TAG: String? = "FavroiteRepository"
    private var firestore = FirebaseFirestore.getInstance()

    suspend fun getFavorites(): ArrayList<GetFavoriteModel> {
        val list: ArrayList<GetFavoriteModel> = ArrayList()
        CoroutineScope(Dispatchers.Main).async {
            firestore.collection("Favorites").addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }
                list.clear()
                try {
                    val document = snapshot!!.toObjects(GetFavoriteModel::class.java)
                    for (i in document) {
                        val favoriteModel: GetFavoriteModel = GetFavoriteModel()
                        favoriteModel.favId = i.favId
                        favoriteModel.userId = i.userId
                        favoriteModel.favList = i.favList!!.toMutableList()
                        list.add(favoriteModel)
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "getFavorites: ${e.message}")
                }
            }
            delay(2000)
        }.await()
        return list
    }


}