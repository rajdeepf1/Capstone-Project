package com.example.pizza_singh_capstone_project.viewmodels

import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.interfaces.FireStoreResponseCallback
import com.example.pizza_singh_capstone_project.models.FeedbackModel
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.repositories.FeedbackRepository
import com.example.pizza_singh_capstone_project.repositories.LoginSignUpRepository
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.Coroutines
import com.example.pizza_singh_capstone_project.utils.InternetConnectionCheck
import com.example.pizza_singh_capstone_project.utils.SharedPref
import com.google.android.material.radiobutton.MaterialRadioButton
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(val feedBackRepository: FeedbackRepository) :
    ViewModel() {

    private val TAG: String? = FeedbackViewModel::class.java.name

    val editTextFeedback = MutableLiveData<String>("")

    fun onFeedbackButtonClick(view: View) {
        if (editTextFeedback.value.isNullOrBlank()) {
            Constant.showToast(view.context, "Please enter your valuable feedback!")
        } else {
            val loginSignupModel: LoginSignupModel = SharedPref.getUserObject(view.context)

            Coroutines.main {
                val resp = feedBackRepository.insertData(
                    FeedbackModel(
                        Date().time,
                        loginSignupModel.userId,
                        loginSignupModel.name,
                        loginSignupModel.phoneNumber,
                        editTextFeedback.value.toString()
                    )
                )
                if (resp) {
                    Constant.showToast(view.context, "Your feedback has been submitted!")
                    editTextFeedback.value = ""
                } else {
                    Constant.showToast(view.context, "Something went wrong!")
                }
            }
        }
    }

}