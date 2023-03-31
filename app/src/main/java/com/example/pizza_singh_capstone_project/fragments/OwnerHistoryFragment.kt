package com.example.pizza_singh_capstone_project.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.adapters.OrderHistoryListAdapter
import com.example.pizza_singh_capstone_project.databinding.FragmentOwnerAccountBinding
import com.example.pizza_singh_capstone_project.databinding.FragmentOwnerHistoryBinding
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.viewmodels.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerHistoryFragment : Fragment() {

    private var _binding: FragmentOwnerHistoryBinding? = null

    private val binding get() = _binding!!

    private val TAG: String = "OwnerHistoryFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOwnerHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

                val viewModel = ViewModelProvider(this).get(
            OrderHistoryViewModel::class.java
        )
        binding.ownerOrderHistoryFragmentViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getOrders()

        viewModel.ordersList.observe(viewLifecycleOwner, Observer {

            when (it) {
                is NetworkResult.Loading -> {
                    //binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    val obj = it.data!!
                    Log.d(TAG, "onCreateViewOrdersList: ${obj}")

                    // create  layoutManager
                    val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

                    // pass it to rvLists layoutManager
                    binding.recyclerView.setLayoutManager(layoutManager)

                    val adapter: OrderHistoryListAdapter =
                        OrderHistoryListAdapter(obj, requireContext())
                    binding.recyclerView.adapter = adapter

                    binding.progressBar.hide()


                }

                is NetworkResult.Error -> {
                    binding.progressBar.hide()
                    Constant.showToast(requireContext(),it.message.toString())
                }
                else -> {
                    binding.progressBar.hide()
                }
            }


        })

        return view
    }

}