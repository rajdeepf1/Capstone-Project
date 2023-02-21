package com.example.pizza_singh_capstone_project.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.FragmentAccountBinding
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.utils.SharedPref
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.utils.show
import kotlinx.coroutines.delay
import kotlin.math.log

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding?  = null

    private val binding get() = _binding!!

    private val TAG: String = "AccountFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val view = binding.root

//        binding.loginBtn.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_loginFragment)
//        }

        binding.progressBar.show()

        val loginSignupModel: LoginSignupModel = SharedPref.getUserObject(requireContext())
        val userId: Long = loginSignupModel.userId

        if (userId == 0L){
            Log.d(TAG, "onCreateView: ${loginSignupModel}")
            Handler().postDelayed(Runnable {
                binding.progressBar.hide()
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_loginFragment)
            },2000)
        }

        return view
    }

}