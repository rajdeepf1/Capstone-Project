package com.example.pizza_singh_capstone_project.viewmodels

import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.interfaces.FireStoreResponseCallback
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.repositories.LoginSignUpRepository
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.Coroutines
import com.example.pizza_singh_capstone_project.utils.InternetConnectionCheck
import com.google.android.material.radiobutton.MaterialRadioButton
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpFragmentViewModel @Inject constructor(val loginSignUpRepository: LoginSignUpRepository) : ViewModel() {

    private val TAG: String? = SignUpFragmentViewModel::class.java.name

    var isVisible = MutableLiveData(View.GONE)

    var isOwner = false

    val editTextNameData = MutableLiveData<String>("")
    val editTextEmailData = MutableLiveData<String>("")
    val editTextPhoneNumberData = MutableLiveData<String>("")
    val editTextAddressData = MutableLiveData<String>("")
    val editTextPasswordData = MutableLiveData<String>("")
    val editTextOwnerData = MutableLiveData<String>("")

    var fireStoreResponseCallback: FireStoreResponseCallback? = null


    fun onBackClicked(view: View) {
        Navigation.findNavController(view).navigateUp()
    }

    fun onSignUpButtonClick(view: View) {
        if (TextUtils.isEmpty(editTextNameData.value.toString())) {
            Constant.showToast(view.context, "Please enter the name!")
        } else if (!Constant.isValidEmail(editTextEmailData.value.toString())) {
            Constant.showToast(view.context, "Please enter the valid email-id!")
        } else if (editTextPhoneNumberData.value.toString().length > 0 && editTextPhoneNumberData.value.toString().length != 10) {
            Constant.showToast(view.context, "Please enter the valid mobile number!")
        } else if (TextUtils.isEmpty(editTextAddressData.value.toString())) {
            Constant.showToast(view.context, "Please enter the valid address!")
        } else if (!Constant.isValidPassword(editTextPasswordData.value.toString())) {
            Constant.showToast(
                view.context,
                "Password must contain minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character"
            )
        } else if (isOwner && TextUtils.isEmpty(editTextOwnerData.value.toString())) {
            Constant.showToast(view.context, "Please enter the Owner-Id")
        } else if (isOwner && !TextUtils.isEmpty(editTextOwnerData.value.toString()) && !editTextOwnerData.value.toString()
                .equals(Constant.ownerId)
        ) {
            Constant.showToast(view.context, "Incorrect Owner's Id!")
        } else {

            val loginSignupModel = LoginSignupModel()

            loginSignupModel.name = editTextNameData.value.toString()
            loginSignupModel.email = editTextEmailData.value.toString()
            loginSignupModel.phoneNumber = editTextPhoneNumberData.value.toString()
            loginSignupModel.address = editTextAddressData.value.toString()
            loginSignupModel.password = editTextPasswordData.value.toString()
            loginSignupModel.isOwner = isOwner
            loginSignupModel.userId = System.currentTimeMillis()
            if (InternetConnectionCheck.isOnline(view.context)) {
                fireStoreResponseCallback?.onStarted()
                Coroutines.main {
                    val response: Boolean = loginSignUpRepository.insertData(loginSignupModel)
                    if (response) {
                        Log.d(TAG, "onSignUpButtonClick: here iffff")
                        fireStoreResponseCallback?.onSuccess(response)
                        onBackClicked(view)
                        return@main
                    } else {
                        Log.d(TAG, "onSignUpButtonClick: here elseee")
                        fireStoreResponseCallback?.onFailure("Signup Failed")
                    }
                }
            } else {
                fireStoreResponseCallback?.onFailure("No Internet Connection!")
            }


        }
    }

    fun onRadioButtonClicked(view: View): Unit {
        if (view is MaterialRadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioUser ->
                    if (checked) {
                        isVisible.value = View.GONE
                        isOwner = false
                    }
                R.id.radioOwner ->
                    if (checked) {
                        isVisible.value = View.VISIBLE
                        isOwner = true
                    }
            }
        }
    }

}