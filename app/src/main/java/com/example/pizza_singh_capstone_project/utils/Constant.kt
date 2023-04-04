package com.example.pizza_singh_capstone_project.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast


object Constant {
    const val sharedPreferences : String = "sharedPreferences"
    const val dateFormat : String = "EEE, MMM d, yyyy"
    const val setTimeOutDuration : Long = 4000
    const val ownerId : String = "1234"
    const val sharedPrefUserObjectKey: String = "user-obj-sharedPrefKey"
    const val REQUEST_PHONE_CALL= 1
    const val PHONE_NUMBER = "+16478937103"


    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIF_ID = 0

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
    // Password must contain minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        //if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
        if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false
        return true
    }
    fun showToast(context: Context,message:String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}