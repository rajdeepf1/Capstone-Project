package com.example.pizza_singh_capstone_project.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.CustomOwnerSubOrderListBinding
import com.example.pizza_singh_capstone_project.models.OwnerCartModel


class OwnerCartSubListAdapter(
    private val list: ArrayList<OwnerCartModel>,
    private val context: Context,
) :
    RecyclerView.Adapter<OwnerCartSubListAdapter.HoursViewHolder>() {

    private var TAG: String = "OrderListAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val binding = CustomOwnerSubOrderListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HoursViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
        with(holder) {

            try {
                val model = list[position]
                binding.textViewName.text = model.productName
                binding.textViewDescription.text = model.extraThings
                Glide.with(context)
                    .load(model.productImage)
                    .into(binding.imageView)

                binding.textViewPrice.text = "$ "+model.productPrice

                binding.textViewQyt.text = "Qyt:  "+model.qyt.toString()

                Log.d(TAG, "onBindViewHolder: ${list.toString()}")

                //Log.d(TAG, "onBindViewHolder Checkkkkkk: ${model.isVeg}")
                if (model.isVeg) {
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
            }catch (e:Exception){
                Log.d(TAG, "onBindViewHolder: ${e.message}")
            }
           

        }
    }

    inner class HoursViewHolder(val binding: CustomOwnerSubOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)

}