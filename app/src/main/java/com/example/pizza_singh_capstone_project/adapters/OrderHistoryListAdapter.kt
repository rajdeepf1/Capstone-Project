package com.example.pizza_singh_capstone_project.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.CustomOrderHistoryListBinding
import com.example.pizza_singh_capstone_project.models.*
import com.google.firebase.firestore.FirebaseFirestore

class OrderHistoryListAdapter(
    private var list: ArrayList<OrderHistoryModel>,
    private val context: Context
) :
    RecyclerView.Adapter<OrderHistoryListAdapter.HoursViewHolder>() {


    private val TAG: String? = OrderHistoryListAdapter::class.java.name

    private val viewPool = RecyclerView.RecycledViewPool()

    val firestore = FirebaseFirestore.getInstance()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val binding = CustomOrderHistoryListBinding
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



                 if (orderStatus!!.equals("accept")){
                     binding.textViewStatus.text = "Accepted"
                     binding.textViewStatus.setTextColor(Color.parseColor("#4CAF50"))
                 }else{
                     binding.textViewStatus.text = "Rejected"
                     binding.textViewStatus.setTextColor(Color.parseColor("#F44336"))
                 }

                // create  layoutManager
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

                // pass it to rvLists layoutManager
                binding.recyclerView.setLayoutManager(layoutManager)

                try {
                    binding.recyclerView.apply {
                        adapter =
                            OwnerCartSubListAdapter(orderList as ArrayList<OwnerCartModel>, context)
                        setRecycledViewPool(viewPool)
                    }
                }catch (e:Exception){
                    Log.d(TAG, "onBindViewHolder: ${e.message}")
                }


            }
        }
    }


    inner class HoursViewHolder(val binding: CustomOrderHistoryListBinding) :
        RecyclerView.ViewHolder(binding.root)

}