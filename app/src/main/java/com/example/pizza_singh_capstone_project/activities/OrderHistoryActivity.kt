package com.example.pizza_singh_capstone_project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.adapters.OrderHistoryListAdapter
import com.example.pizza_singh_capstone_project.databinding.ActivityOrderHistoryBinding
import com.example.pizza_singh_capstone_project.databinding.ActivityOwnerHomeBinding
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.viewmodels.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private val TAG: String = "OrderHistoryActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProvider(this).get(
            OrderHistoryViewModel::class.java
        )
        binding.ownerOrderHistoryFragmentViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getOrders()

        viewModel.ordersList.observe(this, Observer {

            when (it) {
                is NetworkResult.Loading -> {
                    //binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    val obj = it.data!!
                    Log.d(TAG, "onCreateViewOrdersList: ${obj}")

                    // create  layoutManager
                    val layoutManager: RecyclerView.LayoutManager =
                        LinearLayoutManager(applicationContext)

                    // pass it to rvLists layoutManager
                    binding.recyclerView.setLayoutManager(layoutManager)

                    val adapter: OrderHistoryListAdapter =
                        OrderHistoryListAdapter(obj, applicationContext)
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