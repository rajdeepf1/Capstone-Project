package com.example.pizza_singh_capstone_project.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.ActivityOwnerHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OwnerHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerHomeBinding
    private val TAG: String = "OwnerHomeActivity"
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Define ActionBar object
        // Define ActionBar object
        val actionBar: ActionBar?
        actionBar = supportActionBar

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        val colorDrawable = ColorDrawable(Color.parseColor("#E0AA58"))

        // Set BackgroundDrawable

        // Set BackgroundDrawable
        actionBar?.setBackgroundDrawable(colorDrawable)

        navController = Navigation.findNavController(this, R.id.activity_owner_main_nav_host_fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationViewOwner, navController)

    }
}