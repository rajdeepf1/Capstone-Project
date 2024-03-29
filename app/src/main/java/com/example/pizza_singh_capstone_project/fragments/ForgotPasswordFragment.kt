package com.example.pizza_singh_capstone_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.pizza_singh_capstone_project.databinding.FragmentForgotPasswordBinding
import com.example.pizza_singh_capstone_project.repositories.ForgotPasswordRepository
import com.example.pizza_singh_capstone_project.viewmodels.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private val TAG: String? = ForgotPasswordFragment::class.java.name

    private var _binding: FragmentForgotPasswordBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        //val forgotPasswordRepository = ForgotPasswordRepository()
        val viewModel = ViewModelProvider(this).get(
            ForgotPasswordViewModel::class.java)
        binding.forgotFragmentViewModel = viewModel
        binding.lifecycleOwner = this


        return view
    }

}