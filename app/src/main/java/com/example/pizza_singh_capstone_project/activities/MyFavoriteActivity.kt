package com.example.pizza_singh_capstone_project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.adapters.FavoriteListAdapter
import com.example.pizza_singh_capstone_project.adapters.OrderHistoryListAdapter
import com.example.pizza_singh_capstone_project.databinding.ActivityMyFavoriteBinding
import com.example.pizza_singh_capstone_project.databinding.ActivityOrderHistoryBinding
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.viewmodels.FavoriteViewModel
import com.example.pizza_singh_capstone_project.viewmodels.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyFavoriteBinding
    private val TAG: String = "MyFavoriteActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        val viewModel = ViewModelProvider(this).get(
            FavoriteViewModel::class.java
        )
        binding.favoriteViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getFavorites()

        viewModel.favList.observe(this, Observer {

            when (it) {
                is NetworkResult.Loading -> {
                    //binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    val obj = it.data!!
                    Log.d(TAG, "onCreateViewFavList: ${obj}")

                    // create  layoutManager
                    val layoutManager: RecyclerView.LayoutManager =
                        LinearLayoutManager(applicationContext)

                    // pass it to rvLists layoutManager
                    binding.recyclerView.setLayoutManager(layoutManager)

                    val adapter: FavoriteListAdapter =
                        FavoriteListAdapter(obj, applicationContext)
                    binding.recyclerView.adapter = adapter

                    binding.progressBar.hide()


                }

                is NetworkResult.Error -> {
                    binding.progressBar.hide()
                    Constant.showToast(applicationContext, it.message.toString())
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