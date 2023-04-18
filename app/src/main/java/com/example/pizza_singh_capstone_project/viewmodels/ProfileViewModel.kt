package com.example.pizza_singh_capstone_project.viewmodels

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.repositories.ProfileRepository
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.Coroutines
import com.example.pizza_singh_capstone_project.utils.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val TAG: String? = ProfileViewModel::class.java.name
    private var loginSignupModel: LoginSignupModel

    val editTextNameData = MutableLiveData<String>("")
    val editTextEmailData = MutableLiveData<String>("")
    val editTextPhoneNumberData = MutableLiveData<String>("")
    val editTextAddressData = MutableLiveData<String>("")
    var responseData: MutableLiveData<NetworkResult<String>> = MutableLiveData()

    init {
        loginSignupModel = SharedPref.getUserObject(context = context)

        editTextNameData.value = loginSignupModel.name
        editTextEmailData.value = loginSignupModel.email
        editTextPhoneNumberData.value = loginSignupModel.phoneNumber
        editTextAddressData.value = loginSignupModel.address
    }

    fun onUpdateButtonClick(view: View) {
        if (TextUtils.isEmpty(editTextNameData.value.toString())) {
            Constant.showToast(view.context, "Please enter the name!")
        } else if (!Constant.isValidEmail(editTextEmailData.value.toString())) {
            Constant.showToast(view.context, "Please enter the valid email-id!")
        } else if (editTextPhoneNumberData.value.toString().length > 0 && editTextPhoneNumberData.value.toString().length != 10) {
            Constant.showToast(view.context, "Please enter the valid mobile number!")
        } else if (TextUtils.isEmpty(editTextAddressData.value.toString())) {
            Constant.showToast(view.context, "Please enter the valid address!")
        } else {
            Coroutines.main {
                responseData.value = profileRepository.updateUser(
                    loginSignupModel.userId,
                    name = editTextNameData.value.toString(),
                    email = editTextEmailData.value.toString(),
                    mobile = editTextPhoneNumberData.value.toString(),
                    address = editTextAddressData.value.toString()
                )
            }
        }
    }

}