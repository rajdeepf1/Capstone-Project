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
import com.example.pizza_singh_capstone_project.databinding.FragmentSignUpBinding
import com.example.pizza_singh_capstone_project.factories.SignupFactory
import com.example.pizza_singh_capstone_project.interfaces.FireStoreResponseCallback
import com.example.pizza_singh_capstone_project.repositories.LoginSignUpRepository
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.utils.show
import com.example.pizza_singh_capstone_project.viewmodels.SignUpFragmentViewModel

class SignUpFragment : Fragment(), FireStoreResponseCallback {

    private val TAG: String? = SignUpFragment::class.java.name
    private var _binding: FragmentSignUpBinding?  = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        val loginSignUpRepository: LoginSignUpRepository = LoginSignUpRepository()
        val viewModel = ViewModelProvider(this, SignupFactory(loginSignUpRepository)).get(SignUpFragmentViewModel::class.java)
        binding.signUpFragmentViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.fireStoreResponseCallback = this

        return view
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(data: Any) {
        Log.d(TAG, "onSuccess: ${data}")
        if (data as Boolean){
            binding.progressBar.hide()
            Constant.showToast(requireContext(),"Signup Success!")
        }
    }

    override fun onFailure(message: String) {
        Log.d(TAG, "onFailure: ${message}")
        binding.progressBar.hide()
        Constant.showToast(requireContext(),"Signup Failure!")
    }

}