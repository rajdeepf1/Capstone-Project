package com.example.pizza_singh_capstone_project.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pizza_singh_capstone_project.repositories.LoginSignUpRepository
import com.example.pizza_singh_capstone_project.viewmodels.SignUpFragmentViewModel

class SignupFactory(private val loginSignUpRepository: LoginSignUpRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpFragmentViewModel(loginSignUpRepository) as T
    }
}