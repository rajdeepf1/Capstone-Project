package com.example.pizza_singh_capstone_project.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.repositories.ForgotPasswordRepository
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.Coroutines
import com.example.pizza_singh_capstone_project.utils.InternetConnectionCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val forgotPasswordRepository: ForgotPasswordRepository) :
    ViewModel() {

    private val TAG: String? = ForgotPasswordViewModel::class.java.name
    var userResponse: MutableLiveData<NetworkResult<LoginSignupModel>> = MutableLiveData()

    val editTextEmailData = MutableLiveData<String>("")
    val editTextPhoneNumberData = MutableLiveData<String>("")
    val editTextPasswordData = MutableLiveData<String>("")

    fun onBackClicked(view: View) {
        Navigation.findNavController(view).navigateUp()
    }

    fun onChangeButtonClick(view: View) {
        if (editTextEmailData.value.isNullOrBlank() || !Constant.isValidEmail(editTextEmailData.value.toString())) {
            Constant.showToast(view.context, "Please enter the valid email id!")
        } else if (editTextPhoneNumberData.value.toString().length > 0 && editTextPhoneNumberData.value.toString().length != 10) {
            Constant.showToast(view.context, "Please enter the phone number!")
        } else if (!Constant.isValidPassword(editTextPasswordData.value.toString())) {
            Constant.showToast(
                view.context,
                "Password must contain minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character"
            )
        } else {
            if (InternetConnectionCheck.isOnline(view.context)) {
                Coroutines.main {
                    val data: NetworkResult<LoginSignupModel> = forgotPasswordRepository.getUserDataAndCheckIfExists(editTextEmailData.value.toString(), editTextPhoneNumberData.value.toString()) as NetworkResult<LoginSignupModel>
                    val userID = data.data?.userId

                }
            } else {
                Constant.showToast(view.context, "No Internet Connection!")
            }
        }
    }

}