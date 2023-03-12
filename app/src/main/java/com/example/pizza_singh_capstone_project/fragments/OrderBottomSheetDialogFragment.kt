package com.example.pizza_singh_capstone_project.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pizza_singh_capstone_project.R
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

        list?.map {
            try {
                var prodPrice: Double = it.productPrice.toDouble()
                Log.d(
                    TAG, "onBindViewHolder: ${String.format("%.2f", prodPrice * it.qyt)}"
                )
            } catch (e: Exception) {
                Log.d(TAG, "onBindViewHolder: ${e.message}")
            }
        }

        return view

    }

}