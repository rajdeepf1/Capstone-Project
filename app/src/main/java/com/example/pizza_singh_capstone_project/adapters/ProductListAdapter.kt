package com.example.pizza_singh_capstone_project.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.activities.ProductDetailsActivity
import com.example.pizza_singh_capstone_project.databinding.CustomProductListLayoutBinding
import com.example.pizza_singh_capstone_project.models.ProductModel

class ProductListAdapter(private var list: List<ProductModel>, private val context: Context) :
    RecyclerView.Adapter<ProductListAdapter.HoursViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val binding = CustomProductListLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HoursViewHolder(binding)
    }

    override fun getItemCount() = list.size

    // method for filtering our recyclerview items.
    fun filterList(filterlist: ArrayList<ProductModel>) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {

                binding.cardView.animation =
                    AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim)

                binding.textViewName.text = productName
                binding.textViewDescription.text = productDescription[0]
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

                binding.cardView.setOnClickListener(View.OnClickListener {
                    context.startActivity(
                        Intent(context, ProductDetailsActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("productName", productName)
                            .putExtra("productId", productId)
                            .putExtra("productPrice", productPrice)
                            .putExtra("productImage", productImage)
                            .putExtra("isVeg", isVeg)
                            .putStringArrayListExtra("productDescription", productDescription)
                    )
                })

            }
        }
    }

    inner class HoursViewHolder(val binding: CustomProductListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

}