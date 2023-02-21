package com.example.pizza_singh_capstone_project.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.adapters.ProductListAdapter
import com.example.pizza_singh_capstone_project.databinding.ActivityProductListBinding
import com.example.pizza_singh_capstone_project.factories.ProductListFactory
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.ProductModel
import com.example.pizza_singh_capstone_project.repositories.ProductListRepository
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.utils.show
import com.example.pizza_singh_capstone_project.viewmodels.ProductListViewModel

class ProductListActivity : AppCompatActivity() {
    private val TAG: String? = "ProductListActivity"
    private lateinit var binding: ActivityProductListBinding
    private lateinit var  adapter: ProductListAdapter
    lateinit var list: ArrayList<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);


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

                    list = obj as ArrayList<ProductModel>

                    // create  layoutManager
                    val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)

                    // pass it to rvLists layoutManager
                    binding.rvList.setLayoutManager(layoutManager)

                    adapter = ProductListAdapter(list,applicationContext)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    // calling on create option menu
    // layout to inflate our menu file.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu)

        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        // getting search view of our item.
        val searchView: SearchView = searchItem.getActionView() as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<ProductModel> = ArrayList()

        // running a for loop to compare elements.
        for (item in list) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.productName.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Constant.showToast(applicationContext,"No Data Found...")
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }


}