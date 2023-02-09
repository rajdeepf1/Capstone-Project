package com.example.pizza_singh_capstone_project.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.google.gson.Gson

class SharedPref {

    companion object {
        val TAG: String = SharedPref::class.java.name

        fun saveUserObject(context: Context, data: LoginSignupModel):Boolean {
            try {
                val sharedPreferences = context.getSharedPreferences(
                    Constant.sharedPreferences,
                    AppCompatActivity.MODE_PRIVATE
                )

                val editor = sharedPreferences.edit()

                val gson = Gson()

                val value: String = gson.toJson(data)

                editor.putString(Constant.sharedPrefUserObjectKey, value)

                editor.apply()
                return true
            }catch (e: Exception){
                Log.d(TAG, e.message.toString())
                return false
            }

        }

        fun getUserObject(context: Context): LoginSignupModel {
            val sharedPreferences = context.getSharedPreferences(
                Constant.sharedPreferences,
                AppCompatActivity.MODE_PRIVATE
            )

            val gson = Gson()
            val json: String? = sharedPreferences.getString(Constant.sharedPrefUserObjectKey, "")
            val obj: LoginSignupModel = gson.fromJson(json, LoginSignupModel::class.java)

            return obj
        }

    }


}