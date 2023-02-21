package com.example.pizza_singh_capstone_project.utils

import android.content.Context
import android.net.ConnectivityManager

object InternetConnectionCheck {

    fun isOnline(context: Context): Boolean {

        //stackover flow code
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected

        //google code
        /*val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected*/
    }

}