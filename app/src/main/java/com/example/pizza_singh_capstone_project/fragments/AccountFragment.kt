package com.example.pizza_singh_capstone_project.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.activities.MyFavoriteActivity
import com.example.pizza_singh_capstone_project.activities.OrderHistoryActivity
import com.example.pizza_singh_capstone_project.databinding.FragmentAccountBinding
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.SharedPref
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.utils.show
import kotlinx.coroutines.delay
import kotlin.math.log

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

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

        binding.progressBar.show()

        val loginSignupModel: LoginSignupModel = SharedPref.getUserObject(requireContext())
        val userId: Long = loginSignupModel.userId
        val userName: String = loginSignupModel.name

        if (userId == 0L) {
            Log.d(TAG, "onCreateView: ${loginSignupModel}")
            binding.flAccount.visibility = View.GONE
            Handler().postDelayed(Runnable {
                binding.progressBar.hide()
                try {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_accountFragment_to_loginFragment)
                } catch (e: Exception) {
                    Log.d(TAG, "onCreateView: ${e.message}")
                }
            }, 1000)
        } else {
            binding.progressBar.hide()
            try {
                binding.flAccount.visibility = View.VISIBLE
                binding.mark.text = userName[0].toString()
                binding.textView3.text = "${userName},"

                binding.logoutButton.setOnClickListener {
                    SharedPref.clearSharedPrefObject(requireContext())
                    try {
                        Navigation.findNavController(view)
                            .navigate(R.id.action_accountFragment_to_loginFragment)
                    } catch (e: Exception) {
                        Log.d(TAG, "onCreateView: ${e.message}")
                    }
                }

            } catch (e: Exception) {
                Log.d(TAG, "onCreateView: ${e.message}")
            }

        }

        binding.cardView2.setOnClickListener{
            startActivity(Intent(requireContext(),OrderHistoryActivity::class.java))
        }

        binding.cardView3.setOnClickListener{
            startActivity(Intent(requireContext(),MyFavoriteActivity::class.java))
        }

        val aboutBottomSheetDialogFragment = AboutBottomSheetDialogFragment()

        binding.cardView4.setOnClickListener {
            aboutBottomSheetDialogFragment.show(
                (context as FragmentActivity).supportFragmentManager,
                "aboutBottomSheetDialogFragment"
            )

        }

        val feedbackBottomSheetDialogFragment = FeedbackBottomSheetDialogFragment()
        binding.cardView5.setOnClickListener {
            feedbackBottomSheetDialogFragment.show(
                (context as FragmentActivity).supportFragmentManager,
                "feedbackBottomSheetDialogFragment"
            )
        }

        binding.cardView6.setOnClickListener {
            // cheacking permission
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),
                    Constant.REQUEST_PHONE_CALL
                )
            } else {
                makeCall()
            }
        }

        return view
    }

    private fun makeCall() {
        val numberText = Constant.PHONE_NUMBER
        val intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:$numberText"))
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Constant.showToast(requireContext(), "Permission denied")
            return
        }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constant.REQUEST_PHONE_CALL) {
            makeCall()
        }
    }

}