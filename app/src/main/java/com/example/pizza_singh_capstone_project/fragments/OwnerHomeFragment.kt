package com.example.pizza_singh_capstone_project.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.activities.ProductListActivity
import com.example.pizza_singh_capstone_project.adapters.HomeGridAdapter
import com.example.pizza_singh_capstone_project.adapters.OrderListAdapter
import com.example.pizza_singh_capstone_project.adapters.OwnerOrderListAdapter
import com.example.pizza_singh_capstone_project.databinding.FragmentHomeBinding
import com.example.pizza_singh_capstone_project.databinding.FragmentOwnerHomeBinding
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.ORDER_STATUS
import com.example.pizza_singh_capstone_project.models.OwnerCartModel
import com.example.pizza_singh_capstone_project.models.OwnerOrderModel
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.utils.show
import com.example.pizza_singh_capstone_project.viewmodels.HomeFragmentViewModel
import com.example.pizza_singh_capstone_project.viewmodels.OwnerHomeFragmentViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class OwnerHomeFragment : Fragment() {

    private val TAG: String? = OwnerHomeFragment::class.java.name

    private var _binding: FragmentOwnerHomeBinding? = null

    private val binding get() = _binding!!

    private var firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOwnerHomeBinding.inflate(inflater, container, false)
        val view = binding.root

//        val viewModel = ViewModelProvider(this).get(
//            OwnerHomeFragmentViewModel::class.java
//        )
//        binding.ownerHomeFragmentViewModel = viewModel
//        binding.lifecycleOwner = this
//
//        viewModel.getOrders()
//
//        viewModel.ordersList.observe(viewLifecycleOwner, Observer {
//
//            when (it) {
//                is NetworkResult.Loading -> {
//                    //binding.progressBar.show()
//                }
//
//                is NetworkResult.Success -> {
//                    val obj = it.data!!
//                    Log.d(TAG, "onCreateViewOrdersList: ${obj}")
//
//                    // create  layoutManager
//                    val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
//
//                    // pass it to rvLists layoutManager
//                    binding.recyclerView.setLayoutManager(layoutManager)
//
//                    val adapter: OwnerOrderListAdapter =
//                        OwnerOrderListAdapter(obj, requireContext())
//                    binding.recyclerView.adapter = adapter
//
//                    binding.progressBar.hide()
//
//
//                }
//
//                is NetworkResult.Error -> {
//                    binding.progressBar.hide()
//                    Constant.showToast(requireContext(),it.message.toString())
//                }
//                else -> {
//                    binding.progressBar.hide()
//                }
//            }
//
//
//        })
        binding.textViewNoData.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            getOrders()
        }
        return view
    }

    suspend fun getOrders() {
        binding.progressBar.show()
        val list: ArrayList<OwnerOrderModel> = ArrayList<OwnerOrderModel>()
        firestore.clearPersistence()
        CoroutineScope(Dispatchers.Main).async {
            firestore.collection("Orders").addSnapshotListener { snapshot, e ->
                Log.d(TAG, "getOrders: called")
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }
                Log.d(TAG, "getOrders: called1")
                list.clear()
                snapshot!!.documents.map {
                    val orderId = it.data!!.getValue("orderId") as Long
                    val orderStatus = it.data!!.getValue("orderStatus") as ORDER_STATUS?
                    val userId = it.data!!.getValue("userId") as Long
                    val totalAmount = it.data!!.getValue("totalAmount") as String
                    val orderList: ArrayList<OwnerCartModel> =
                        it.data!!.getValue("orderList") as ArrayList<OwnerCartModel>
                    val data =
                        OwnerOrderModel(orderId, orderStatus, orderList, userId, totalAmount)
                    list.add(data)
                    Log.d(TAG, "getOrders: called2")
                }
                // create  layoutManager
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

                // pass it to rvLists layoutManager
                binding.recyclerView.setLayoutManager(layoutManager)
                binding.progressBar.hide()

                if (list!= null && list.size>0){
                    val adapter =
                        OwnerOrderListAdapter(list, requireContext())
                    binding.recyclerView.adapter = adapter
                    binding.textViewNoData.visibility = View.GONE
                }else{
                    binding.textViewNoData.visibility = View.VISIBLE
                }

            }
            Log.d(TAG, "getOrders: called3")

        }.await()
    }

}