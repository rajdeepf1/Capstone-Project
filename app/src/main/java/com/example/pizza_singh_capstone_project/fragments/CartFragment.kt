package com.example.pizza_singh_capstone_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.adapters.CartsListAdapter
import com.example.pizza_singh_capstone_project.databinding.FragmentCartBinding
import com.example.pizza_singh_capstone_project.databinding.FragmentHomeBinding
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.utils.SharedPref
import com.example.pizza_singh_capstone_project.viewmodels.CartViewModel


class CartFragment : Fragment() {

    private val TAG: String? = CartFragment::class.java.name

    private var _binding: FragmentCartBinding? = null

    private val binding get() = _binding!!

    private lateinit var cartViewModel: CartViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
// Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        // create  layoutManager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

        // pass it to rvLists layoutManager
        binding.rvList.setLayoutManager(layoutManager)

        val loginSignupModel: LoginSignupModel = SharedPref.getUserObject(requireContext())
        val userId: Long = loginSignupModel.userId
        if (userId != 0L) {
            binding.textViewNoDataFoundLoginFirst.visibility = View.INVISIBLE
            cartViewModel.getAllCartData(requireContext(), userId = userId)
                .observe(viewLifecycleOwner, Observer {
                    val adapter: CartsListAdapter = CartsListAdapter(it, requireContext())
                    binding.rvList.adapter = adapter
                })
        } else {
            binding.textViewNoDataFoundLoginFirst.visibility = View.VISIBLE
        }

        return view
    }

}