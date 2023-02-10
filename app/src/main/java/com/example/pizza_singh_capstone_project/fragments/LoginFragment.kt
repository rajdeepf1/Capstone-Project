package com.example.pizza_singh_capstone_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.FragmentLoginBinding
import com.example.pizza_singh_capstone_project.factories.LoginFactory
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.repositories.LoginSignUpRepository
import com.example.pizza_singh_capstone_project.utils.*
import com.example.pizza_singh_capstone_project.viewmodels.LoginFragmentViewModel

class LoginFragment : Fragment() {

    private val TAG: String? = LoginFragment::class.java.name

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        val loginSignUpRepository = LoginSignUpRepository()
        val viewModel = ViewModelProvider(this, LoginFactory(loginSignUpRepository)).get(
            LoginFragmentViewModel::class.java
        )
        binding.loginFragmentViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.userResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }

                is NetworkResult.Success -> {
                    val obj = it.data!!

                    Coroutines.main {
                        SharedPref.saveUserObject(
                            requireContext(),
                            LoginSignupModel(
                                obj.name,
                                obj.email,
                                obj.phoneNumber,
                                obj.address,
                                obj.password,
                                obj.isOwner
                            )
                        )
                    }

                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)

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