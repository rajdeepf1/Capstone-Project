package com.example.pizza_singh_capstone_project.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.activities.OwnerHomeActivity
import com.example.pizza_singh_capstone_project.databinding.FragmentLoginBinding
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.utils.*
import com.example.pizza_singh_capstone_project.viewmodels.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        //val loginSignUpRepository = LoginSignUpRepository()
        val viewModel = ViewModelProvider(this).get(
            LoginFragmentViewModel::class.java
        )
        binding.loginFragmentViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.userResponse.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: ${it.data}")

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
                                    obj.userId,
                                    obj.name,
                                    obj.email,
                                    obj.phoneNumber,
                                    obj.address,
                                    obj.password,
                                    obj.isOwner
                                )
                            )
                        }


                    val handle: Handler = Handler()
                    handle.postDelayed(Runnable {
                        val loginSignupModel: LoginSignupModel = SharedPref.getUserObject(requireContext())

                        if (loginSignupModel.isOwner){
                            startActivity(
                                Intent(requireContext(), OwnerHomeActivity::class.java)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            )
                        }else{
                            Navigation.findNavController(view)
                                .navigate(R.id.action_loginFragment_to_homeFragment)
                        }

                    },2000)



                }

                is NetworkResult.Error -> {
                    Log.d(TAG, "onCreateView: errorrrrr")
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