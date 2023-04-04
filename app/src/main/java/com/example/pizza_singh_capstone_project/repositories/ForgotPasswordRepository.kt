package com.example.pizza_singh_capstone_project.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.pizza_singh_capstone_project.database.AppDatabase
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.utils.Coroutines
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ForgotPasswordRepository @Inject constructor(){
    private val TAG: String? = "ForgotPasswordRepository"

    val firestore = FirebaseFirestore.getInstance()

    suspend fun getUserDataAndCheckIfExists(email: String, number: String) : NetworkResult<LoginSignupModel>? {
        var userResponse: NetworkResult<LoginSignupModel>? = null

        firestore.collection("User")
            .whereEqualTo("email", "${email}")
            .whereEqualTo("phoneNumber", "${number}")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful ) {
                    if (it.result.size() > 0){
                        //userResponse = it.result?.toObjects(LoginSignupModel::class.java)?.get(0)
                        userResponse = it.result?.toObjects(LoginSignupModel::class.java)?.get(0)
                            ?.let { it1 -> NetworkResult.Success(it1) }!!
                    }else{
                        userResponse = NetworkResult.Error("No User Found!")
                    }
                } else {
                    userResponse = NetworkResult.Error("Getting User Data Failed!")
                }
            }.addOnFailureListener(OnFailureListener {
                Log.d(TAG, "getUserData: getUserDataAndCheckIfExists failure")
                userResponse = NetworkResult.Error("Getting User Data Failed!")
            }).await()

        return userResponse
    }

}