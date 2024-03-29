package com.example.pizza_singh_capstone_project.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.pizza_singh_capstone_project.activities.MainActivity
import com.example.pizza_singh_capstone_project.databinding.FragmentOwnerAccountBinding
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.SharedPref
import com.example.pizza_singh_capstone_project.utils.hide
import com.example.pizza_singh_capstone_project.utils.show


class OwnerAccountFragment : Fragment() {

    private var _binding: FragmentOwnerAccountBinding? = null

    private val binding get() = _binding!!

    private val TAG: String = "FragmentOwnerAccountBinding"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOwnerAccountBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.progressBar.show()

        val loginSignupModel: LoginSignupModel = SharedPref.getUserObject(requireContext())
        val userId: Long = loginSignupModel.userId
        val userName: String = loginSignupModel.name

        try {
            binding.progressBar.hide()
            binding.flAccount.visibility = View.VISIBLE
            binding.mark.text = userName[0].toString()
            binding.textView3.text = "${userName},"

            binding.logoutButton.setOnClickListener {
                SharedPref.clearSharedPrefObject(requireContext())
                try {
                    startActivity(
                        Intent(
                            requireContext(),
                            MainActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    )
                } catch (e: Exception) {
                    Log.d(TAG, "onCreateView: ${e.message}")
                }
            }

        } catch (e: Exception) {
            Log.d(TAG, "onCreateView: ${e.message}")
        }

        binding.cardView2.setOnClickListener {
            val aboutBottomSheetDialogFragment = AboutBottomSheetDialogFragment()
            aboutBottomSheetDialogFragment.show(
                (context as FragmentActivity).supportFragmentManager,
                "aboutBottomSheetDialogFragment"
            )

        }

        binding.cardView3.setOnClickListener {
            val feedbackBottomSheetDialogFragment = FeedbackBottomSheetDialogFragment()
            feedbackBottomSheetDialogFragment.show(
                (context as FragmentActivity).supportFragmentManager,
                "feedbackBottomSheetDialogFragment"
            )
        }

        binding.cardView4.setOnClickListener {
            // cheacking permission
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CALL_PHONE),
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