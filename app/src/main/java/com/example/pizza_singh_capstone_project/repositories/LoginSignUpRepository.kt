package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
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

    suspend fun getUserData(email: String, password: String) : LoginSignupModel? {
        var userResponse:LoginSignupModel? = null
        firestore.collection("User")
            .whereEqualTo("email", "${email}")
            .whereEqualTo("password", "${password}")
            .get()
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful){
                    userResponse = it.result.toObjects(LoginSignupModel::class.java).get(0)
                }else{
                    userResponse = null
                }
            }).addOnFailureListener(OnFailureListener {
                Log.d(TAG, "getUserData: login failure")
                userResponse = null
            }).await()

        return userResponse
    }

}