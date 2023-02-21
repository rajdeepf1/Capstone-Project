package com.example.pizza_singh_capstone_project.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pizza_singh_capstone_project.activities.ProductListActivity
import com.example.pizza_singh_capstone_project.adapters.HomeGridAdapter
import com.example.pizza_singh_capstone_project.databinding.FragmentHomeBinding
import com.example.pizza_singh_capstone_project.factories.HomeFactory
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.repositories.HomeRepository
import com.example.pizza_singh_capstone_project.utils.*
import com.example.pizza_singh_capstone_project.viewmodels.HomeFragmentViewModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class HomeFragment : Fragment() {

    private val TAG: String? = HomeFragment::class.java.name

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)

        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val homeRepository = HomeRepository()
        val viewModel = ViewModelProvider(this, HomeFactory(homeRepository)).get(
            HomeFragmentViewModel::class.java
        )
        binding.homeFragmentViewModel = viewModel
        binding.lifecycleOwner = this

        binding.carousel.registerLifecycle(lifecycle)

        viewModel.getBanners()

        viewModel.homeBanners.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                    //binding.progressBar.show()

                }

                is NetworkResult.Success -> {
                    val obj = it.data!!
                    Log.d(TAG, "onCreateView: ${obj}")

                    val list:ArrayList<CarouselItem> = ArrayList()
                    obj.map {
                        list.add(CarouselItem(it.imgPath))
                    }

                    binding.carousel.setData(list)
                }

                is NetworkResult.Error -> {
                    //binding.progressBar.hide()
                    Constant.showToast(requireContext(),it.message.toString())
                }
                else -> {
                    //binding.progressBar.hide()
                }
            }

        })



        viewModel.getCategories()

        viewModel.categoriesList.observe(viewLifecycleOwner, Observer {

            when (it) {
                is NetworkResult.Loading -> {
                    //binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    val obj = it.data!!
                    Log.d(TAG, "onCreateViewCategoriesList: ${obj}")

                    val homeGridAdapter = HomeGridAdapter(requireContext(),obj)
                    binding.gridView.adapter = homeGridAdapter
                    binding.gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        startActivity(Intent(requireContext(),ProductListActivity::class.java)
                            .putExtra("category_name",obj.get(position).categoryName)
                            .putExtra("category_id",obj.get(position).id)
                        )

                    }

                }

                is NetworkResult.Error -> {
                    //binding.progressBar.hide()
                    Constant.showToast(requireContext(),it.message.toString())
                }
                else -> {
                    //binding.progressBar.hide()
                }
            }

        })

        return view
    }

}