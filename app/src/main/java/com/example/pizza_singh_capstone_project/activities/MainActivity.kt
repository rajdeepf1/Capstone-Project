package com.example.pizza_singh_capstone_project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = Navigation.findNavController(this,R.id.activity_main_nav_host_fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        val isCommingFromDifferentScreen : Boolean = intent.getBooleanExtra("isCommingFromDifferentScreen",false)

        Log.d(TAG, "onCreate: ${isCommingFromDifferentScreen}")

        if (isCommingFromDifferentScreen) {
            binding.bottomNavigationView.selectedItemId = R.id.accountFragment
        }

    }
}