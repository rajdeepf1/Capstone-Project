package com.example.pizza_singh_capstone_project.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.adapters.CartsListAdapter
import com.example.pizza_singh_capstone_project.adapters.OrderListAdapter
import com.example.pizza_singh_capstone_project.databinding.FragmentCartBinding
import com.example.pizza_singh_capstone_project.databinding.FragmentOrderBottomSheetDialogBinding
import com.example.pizza_singh_capstone_project.models.CartModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OrderBottomSheetDialogFragment(list: List<CartModel>) : BottomSheetDialogFragment() {

    private val TAG: String? = OrderBottomSheetDialogFragment::class.java.name

    private var _binding: FragmentOrderBottomSheetDialogBinding? = null

    private val binding get() = _binding!!

    private var list: List<CartModel>? = null

    init {
        this.list = list
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderBottomSheetDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        // create  layoutManager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

        // pass it to rvLists layoutManager
        binding.rvList.setLayoutManager(layoutManager)

        val adapter: OrderListAdapter =
            OrderListAdapter(list!!, requireContext())
        binding.rvList.adapter = adapter

        return view

    }

}