package com.example.pizza_singh_capstone_project.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.adapters.ProductListAdapter
import com.example.pizza_singh_capstone_project.databinding.ActivityMainBinding
import com.example.pizza_singh_capstone_project.databinding.ActivityProductListBinding
import com.example.pizza_singh_capstone_project.factories.HomeFactory
import com.example.pizza_singh_capstone_project.factories.ProductListFactory
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.repositories.HomeRepository
import com.example.pizza_singh_capstone_project.repositories.ProductListRepository
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.utils.show
import com.example.pizza_singh_capstone_project.viewmodels.HomeFragmentViewModel
import com.example.pizza_singh_capstone_project.viewmodels.ProductListViewModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class ProductListActivity : AppCompatActivity() {
    private val TAG: String? = "ProductListActivity"
    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val category_name: String= getIntent().getStringExtra("category_name").toString()
        val category_id: String= getIntent().getStringExtra("category_id").toString()
        Log.d(TAG, "onCreate: ${category_name}")

        val productListRepository = ProductListRepository(category_name,category_id)
        val viewModel = ViewModelProvider(this, ProductListFactory(productListRepository)).get(
            ProductListViewModel::class.java
        )
        binding.productListViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getProductList(context = applicationContext)

        viewModel.productList.observe(this, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    val obj = it.data!!
                    Log.d(TAG, "onCreateView: ${obj}")
                    // create  layoutManager
                    val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)

                    // pass it to rvLists layoutManager
                    binding.rvList.setLayoutManager(layoutManager)

                    val adapter: ProductListAdapter = ProductListAdapter(obj,applicationContext)
                    binding.rvList.adapter = adapter

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
}