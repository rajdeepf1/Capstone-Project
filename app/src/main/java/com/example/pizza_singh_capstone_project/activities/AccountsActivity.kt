package com.example.pizza_singh_capstone_project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pizza_singh_capstone_project.databinding.ActivityAccountsBinding
import com.example.pizza_singh_capstone_project.databinding.ActivityMyFavoriteBinding
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.utils.show
import com.example.pizza_singh_capstone_project.viewmodels.FavoriteViewModel
import com.example.pizza_singh_capstone_project.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class AccountsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountsBinding
    private val TAG: String = "AccountsActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProvider(this).get(
            ProfileViewModel::class.java
        )
        binding.profileViewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.responseData.observe(this, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    val obj = it.data!!
                    Log.d(TAG, "onCreateView: ${obj}")
                    Constant.showToast(applicationContext, message = obj)
                    //todo update shared pref here by getting object the response itself
                    binding.progressBar.hide()

                }

                is NetworkResult.Error -> {
                    binding.progressBar.hide()
                    Constant.showToast(applicationContext,it.message.toString())
                }
                else -> {
                    binding.progressBar.hide()
                }
            }
        })

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}