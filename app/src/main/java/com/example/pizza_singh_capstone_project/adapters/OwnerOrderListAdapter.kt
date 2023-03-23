package com.example.pizza_singh_capstone_project.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.CustomOwnerOrderListBinding
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.models.OrderDisplayModel
import com.example.pizza_singh_capstone_project.models.OrdersModel

class OwnerOrderListAdapter(
    private var list: ArrayList<OrdersModel>,
    private val context: Context
) :
    RecyclerView.Adapter<OwnerOrderListAdapter.HoursViewHolder>() {


    private val TAG: String? = OwnerOrderListAdapter::class.java.name

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val binding = CustomOwnerOrderListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HoursViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {

                binding.cardView.animation =
                    AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim)
                binding.textViewOrderId.setText(orderId.toString())

//                try {
//                    binding.textViewProductNames.text= productName
//                } catch (e: Exception) {
//                    Log.d(TAG, "onBindViewHolder: ${e.message}")
//                }

            }
        }
    }

    inner class HoursViewHolder(val binding: CustomOwnerOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)

}