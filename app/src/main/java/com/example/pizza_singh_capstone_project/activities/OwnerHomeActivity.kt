package com.example.pizza_singh_capstone_project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.ActivityMainBinding
import com.example.pizza_singh_capstone_project.databinding.ActivityOwnerHomeBinding

class OwnerHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerHomeBinding
    private val TAG: String = "OwnerHomeActivity"
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = Navigation.findNavController(this, R.id.activity_owner_main_nav_host_fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationViewOwner, navController)

    }
}