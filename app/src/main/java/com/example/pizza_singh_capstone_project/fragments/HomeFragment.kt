package com.example.pizza_singh_capstone_project.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.FragmentHomeBinding
import com.example.pizza_singh_capstone_project.databinding.FragmentLoginBinding
import com.example.pizza_singh_capstone_project.utils.SharedPref
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

        binding.carousel.registerLifecycle(lifecycle)
        val list = mutableListOf<CarouselItem>()


        return view
    }

}