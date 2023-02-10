package com.example.pizza_singh_capstone_project.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.repositories.LoginSignUpRepository
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.Coroutines

class LoginFragmentViewModel(val loginSignUpRepository: LoginSignUpRepository) : ViewModel() {

    private val TAG: String? = LoginFragmentViewModel::class.java.name

    val loginTextField = MutableLiveData<String>()
    val passwordTextField = MutableLiveData<String>()
    var userResponse : MutableLiveData<NetworkResult<LoginSignupModel>> = MutableLiveData()

    fun onLoginButtonClick(view: View): Unit {
        if (!Constant.isValidEmail(loginTextField.value.toString())) {
            Constant.showToast(view.context, "Please enter the valid email-id!")
        } else if (passwordTextField.value.isNullOrBlank()) {
            Constant.showToast(view.context, "Please fill the password!")
        } else {
            userResponse.value = NetworkResult.Loading()
            Coroutines.main {
                val data = loginSignUpRepository.getUserData(
                    loginTextField.value.toString(),
                    passwordTextField.value.toString()
                )
                if (data != null){
                    userResponse.value = NetworkResult.Success(data)
                    NetworkResult.Success(userResponse.value)
                }else{
                    NetworkResult.Error("Login Failed!")
                }
            }
        }
    }

    fun onSignUpButtonClick(view: View): Unit {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment)
    }
}