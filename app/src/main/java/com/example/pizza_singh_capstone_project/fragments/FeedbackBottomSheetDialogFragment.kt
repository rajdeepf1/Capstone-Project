package com.example.pizza_singh_capstone_project.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.FragmentAboutBottomSheetDialogBinding
import com.example.pizza_singh_capstone_project.databinding.FragmentFeedbackBottomSheetDialogBinding
import com.example.pizza_singh_capstone_project.viewmodels.FeedbackViewModel
import com.example.pizza_singh_capstone_project.viewmodels.SignUpFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackBottomSheetDialogFragment : BottomSheetDialogFragment() {

    lateinit var dialog: BottomSheetDialog
    private var _binding: FragmentFeedbackBottomSheetDialogBinding? = null

    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = BottomSheetDialog(requireContext(), theme)

        dialog.dismissWithAnimation = true
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedbackBottomSheetDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        //val loginSignUpRepository: LoginSignUpRepository = LoginSignUpRepository()
        val viewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)
        binding.feedbackViewModel = viewModel
        binding.lifecycleOwner = this

        return view
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

}