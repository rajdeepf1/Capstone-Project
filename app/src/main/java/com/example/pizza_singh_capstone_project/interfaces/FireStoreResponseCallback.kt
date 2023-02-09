package com.example.pizza_singh_capstone_project.interfaces

interface FireStoreResponseCallback {
    fun onStarted()
    fun onSuccess(data: Any)
    fun onFailure(message : String)
}