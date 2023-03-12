package com.example.pizza_singh_capstone_project.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.ActivityMainBinding
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.utils.SharedPref

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val loginSignupModel: LoginSignupModel = SharedPref.getUserObject(applicationContext)

        if (loginSignupModel.isOwner){
            startActivity(
                Intent(applicationContext, OwnerHomeActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }

        navController = Navigation.findNavController(this,R.id.activity_main_nav_host_fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        val isCommingFromDifferentScreen : Boolean = intent.getBooleanExtra("isCommingFromDifferentScreen",false)
        val isCommingFromCartScreenScreen : Boolean = intent.getBooleanExtra("isCommingFromCartScreen",false)

        Log.d(TAG, "onCreate: ${isCommingFromDifferentScreen}")
        Log.d(TAG, "onCreate: isCommingFromCartScreen ${isCommingFromCartScreenScreen}")

        if (isCommingFromDifferentScreen) {
            binding.bottomNavigationView.selectedItemId = R.id.accountFragment
        }
        if (isCommingFromCartScreenScreen){
            binding.bottomNavigationView.selectedItemId = R.id.cartFragment
        }

    }
}