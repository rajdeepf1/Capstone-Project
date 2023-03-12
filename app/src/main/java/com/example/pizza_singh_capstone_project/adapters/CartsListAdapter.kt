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
import com.example.pizza_singh_capstone_project.fragments.OrderBottomSheetDialogFragment
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.utils.Constant


class CartsListAdapter(
    private val list: List<CartModel>,
    private val context: Context,
    private val orderButton: Button,
    private val progressBar: ProgressBar
) :
    RecyclerView.Adapter<CartsListAdapter.HoursViewHolder>() {

    private var TAG: String = "CartsListAdapter"

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


                binding.textQyt.setText(qyt.toString())
                binding.textViewPrice.setText("$ ${productPrice}")
                try {
                    var prodPrice: Double = productPrice.toDouble()

                    binding.imagePlus.setOnClickListener {
                        if (list[position].qyt < 10) {
                            list[position].qyt = qyt + 1
                            binding.textQyt.setText(qyt.toString())
                            binding.textViewPrice.setText(
                                "$ ${
                                    String.format(
                                        "%.2f",
                                        prodPrice * qyt
                                    )
                                }"
                            )
                        } else {
                            Constant.showToast(context, "You cannot order more than 10 items.")
                        }

                    }
                    binding.imageMinus.setOnClickListener {
                        if (list[position].qyt > 1) {
                            list[position].qyt = qyt - 1
                            binding.textQyt.setText(qyt.toString())
                            binding.textViewPrice.setText(
                                "$ ${
                                    String.format(
                                        "%.2f",
                                        prodPrice * qyt
                                    )
                                }"
                            )
                        } else {
                            Constant.showToast(context, "You atleast have one item in the cart")
                        }
                    }

                } catch (e: Exception) {
                    Log.d(TAG, "onBindViewHolder: ${e.message}")
                }

                orderButton.setOnClickListener {
                    Log.d(TAG, "onBindViewHolder: ${list}")

//                    list.map {
//                        try {
//                            var prodPrice: Double = it.productPrice.toDouble()
//                            Log.d(
//                                TAG, "onBindViewHolder: ${ String.format("%.2f", prodPrice * it.qyt) }")
//                        } catch (e: Exception) {
//                            Log.d(TAG, "onBindViewHolder: ${e.message}")
//                        }
//                    }
                    progressBar.visibility = View.VISIBLE

                    Handler().postDelayed(Runnable {
                        progressBar.visibility = View.GONE
                        val orderBottomSheetDialogFragment = OrderBottomSheetDialogFragment(list)
                        orderBottomSheetDialogFragment.show(
                            (context as FragmentActivity).supportFragmentManager,
                            "orderBottomSheetDialogFragment"
                        )
                    }, 2000)

                }

            }
        }
    }


    inner class HoursViewHolder(val binding: CustomCartListBinding) :
        RecyclerView.ViewHolder(binding.root)

}