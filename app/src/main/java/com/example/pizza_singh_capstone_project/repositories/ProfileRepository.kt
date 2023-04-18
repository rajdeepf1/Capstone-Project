package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepository @Inject constructor() {
    private val TAG: String? = "ProfileRepository"
    val firestore = FirebaseFirestore.getInstance()

    suspend fun updateUser(
        userId: Long, name: String, email: String, mobile: String, address: String
    ): NetworkResult<String>? {
        Log.d(TAG, "updateUser: checkkkkk ${userId}")
        var userResponse: NetworkResult<String>? = null

        firestore.collection("User").whereEqualTo("userId", userId).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result.size() > 0) {
                        Log.d(TAG, "updateUser: ${it.result.documents.get(0).id}")
                        val key = it.result.documents.get(0).id
                        val map: HashMap<String, String> = HashMap<String, String>()
                        map.put("name", name)
                        map.put("email", email)
                        map.put("phoneNumber", mobile)
                        map.put("address", address)
                        firestore.collection("User").document(key).update(
                            map as Map<String, String>
                        ).addOnCompleteListener{
                            if (it.isSuccessful){
                                userResponse = NetworkResult.Success("Update Successful")
                            }
                        }

                    } else {
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