package com.example.pizza_singh_capstone_project.models

data class FeedbackModel(var feedBackId: Long = 0,
                         var userId:Long = 0,
                         var userName: String = "",
                         var userNumer: String = "",
                         var feedBackMessage:String = "")
