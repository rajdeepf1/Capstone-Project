package com.example.pizza_singh_capstone_project.adapters

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.CustomCartListBinding
import com.example.pizza_singh_capstone_project.databinding.CustomOrderListBinding
import com.example.pizza_singh_capstone_project.fragments.OrderBottomSheetDialogFragment
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.utils.Constant


class OrderListAdapter(
    private val list: List<CartModel>,
    private val context: Context,
) :
    RecyclerView.Adapter<OrderListAdapter.HoursViewHolder>() {

    private var TAG: String = "OrderListAdapter"
    var sumPrice: Double = 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val binding = CustomOrderListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HoursViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {

                binding.textViewName.text = productName
                binding.textViewDescription.text = extraThings
                Glide.with(holder.itemView.context)
                    .load(productImage)
                    .into(binding.imageView)
                if (isVeg) {
                    binding.textViewName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.veg,
                        0,
                        0,
                        0
                    )
                } else {
                    binding.textViewName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.nonveg,
                        0,
                        0,
                        0
                    )
                }
                try {
                    val prodPrice: Double = productPrice.toDouble()

                    val priceWithQyt: String = "$ ${String.format("%.2f", prodPrice * qyt)}"
                    binding.textViewPrice.setText(priceWithQyt)

                    sumPrice += String.format("%.2f", prodPrice * qyt).toDouble()
                    Log.d(TAG, "onBindViewHolder: ${sumPrice}")

                } catch (e: Exception) {
                    Log.d(TAG, "onBindViewHolder: ${e.message}")
                }
            }
        }
    }


    inner class HoursViewHolder(val binding: CustomOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)

}