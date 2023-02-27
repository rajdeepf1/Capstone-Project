package com.example.pizza_singh_capstone_project.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pizza_singh_capstone_project.repositories.ForgotPasswordRepository
import com.example.pizza_singh_capstone_project.repositories.LoginSignUpRepository
import com.example.pizza_singh_capstone_project.viewmodels.ForgotPasswordViewModel
import com.example.pizza_singh_capstone_project.viewmodels.SignUpFragmentViewModel

class ForgotPasswordFactory(private val forgotPasswordRepository: ForgotPasswordRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForgotPasswordViewModel(forgotPasswordRepository) as T
    }
}