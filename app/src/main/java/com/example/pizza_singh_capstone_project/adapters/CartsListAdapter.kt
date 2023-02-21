package com.example.pizza_singh_capstone_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.CustomCartListBinding
import com.example.pizza_singh_capstone_project.models.CartModel

class CartsListAdapter (private val list: List<CartModel>, private val context: Context) :
    RecyclerView.Adapter<CartsListAdapter.HoursViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val binding = CustomCartListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HoursViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {

                binding.cardView.animation =
                    AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim)

                binding.textViewName.text = productName
                binding.textViewDescription.text = extraThings
                binding.textViewPrice.setText("$ ${productPrice}")
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
                }else{
                    binding.textViewName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.nonveg,
                        0,
                        0,
                        0
                    )
                }

            }
        }
    }

    inner class HoursViewHolder(val binding: CustomCartListBinding) :
        RecyclerView.ViewHolder(binding.root)

}