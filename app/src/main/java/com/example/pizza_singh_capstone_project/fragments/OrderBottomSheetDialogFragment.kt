package com.example.pizza_singh_capstone_project.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.adapters.OrderListAdapter
import com.example.pizza_singh_capstone_project.databinding.FragmentOrderBottomSheetDialogBinding
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.viewmodels.CartViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class OrderBottomSheetDialogFragment(list: List<CartModel>) : BottomSheetDialogFragment() {

    private val TAG: String? = OrderBottomSheetDialogFragment::class.java.name

    private var _binding: FragmentOrderBottomSheetDialogBinding? = null

    private val binding get() = _binding!!

    private var list: List<CartModel>? = null

    private lateinit var cartViewModel: CartViewModel
    lateinit var dialog: BottomSheetDialog


    init {
        this.list = list
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = BottomSheetDialog(requireContext(), theme)
        dialog.dismissWithAnimation=true
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

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderBottomSheetDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)


        // create  layoutManager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

        // pass it to rvLists layoutManager
        binding.rvList.setLayoutManager(layoutManager)

        val adapter: OrderListAdapter =
            OrderListAdapter(list!!, requireContext(),binding.tvSubTotal,binding.totalValueText,binding.placeOrderButton,cartViewModel,dialog)
        binding.rvList.adapter = adapter

        return view

    }

}