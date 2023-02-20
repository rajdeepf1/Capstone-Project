package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginSignUpRepository {
    private val TAG: String? = "LoginSignUpRepository"
    val firestore = FirebaseFirestore.getInstance()

    suspend fun insertData(data: LoginSignupModel): Boolean {
        Log.d(TAG, "insertData: here")
        var resp = false
        firestore.collection("User").add(data)
            .addOnCompleteListener(OnCompleteListener {
                resp = it.isSuccessful
            }).addOnFailureListener(OnFailureListener {
                resp = false
            }).await()
        return resp
    }

    suspend fun getUserData(email: String, password: String) : NetworkResult<LoginSignupModel>? {
        var userResponse:NetworkResult<LoginSignupModel>? = null
        firestore.collection("User")
            .whereEqualTo("email", "${email}")
            .whereEqualTo("password", "${password}")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful ) {
                    if (it.result.size() > 0){
                        //userResponse = it.result?.toObjects(LoginSignupModel::class.java)?.get(0)
                        userResponse = it.result?.toObjects(LoginSignupModel::class.java)?.get(0)
                            ?.let { it1 -> NetworkResult.Success(it1) }!!
                    }else{
                        userResponse = NetworkResult.Error("Login Failed!")
                    }
                } else {
                    userResponse = NetworkResult.Error("Login Failed!")
                }
            }.addOnFailureListener(OnFailureListener {
                Log.d(TAG, "getUserData: login failure")
                userResponse = NetworkResult.Error("Login Failed!")
            }).await()

        return userResponse
    }

}