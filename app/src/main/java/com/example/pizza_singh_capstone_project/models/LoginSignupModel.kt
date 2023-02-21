package com.example.pizza_singh_capstone_project.models

data class LoginSignupModel(var userId: Long = 0,
                            var name:String = "",
                            var email:String = "",
                            var phoneNumber: String = "",
                            var address: String = "",
                            var password: String = "",
                            @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
                            var isOwner:Boolean = false
                            )
