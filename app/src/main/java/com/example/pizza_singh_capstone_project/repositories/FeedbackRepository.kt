package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.FeedbackModel
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FeedbackRepository @Inject constructor(){
    private val TAG: String? = "FeedbackRepository"
    val firestore = FirebaseFirestore.getInstance()

    suspend fun insertData(data: FeedbackModel): Boolean {
        var resp = false
        firestore.collection("Feedbacks").add(data)
            .addOnCompleteListener(OnCompleteListener {
                resp = it.isSuccessful
            }).addOnFailureListener(OnFailureListener {
                resp = false
            }).await()
        return resp
    }

}