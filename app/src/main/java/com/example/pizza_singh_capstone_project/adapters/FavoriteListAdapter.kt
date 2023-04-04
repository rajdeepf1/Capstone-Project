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
import com.example.pizza_singh_capstone_project.databinding.CustomFavListBinding
import com.example.pizza_singh_capstone_project.databinding.CustomOrderHistoryListBinding
import com.example.pizza_singh_capstone_project.models.*
import com.google.firebase.firestore.FirebaseFirestore

class FavoriteListAdapter(
    private var list: ArrayList<GetFavoriteModel>,
    private val context: Context
) :
    RecyclerView.Adapter<FavoriteListAdapter.HoursViewHolder>() {

    private val TAG: String? = FavoriteListAdapter::class.java.name

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val binding = CustomFavListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HoursViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {

                binding.cardView.animation =
                    AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim)
                binding.textViewOrderDate.setText(favId.toString())

                // create  layoutManager
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

                // pass it to rvLists layoutManager
                binding.recyclerView.setLayoutManager(layoutManager)

                try {
                    binding.recyclerView.apply {
                        adapter =
                            OwnerCartSubListAdapter(favList as ArrayList<OwnerCartModel>, context)
                        setRecycledViewPool(viewPool)
                    }
                }catch (e:Exception){
                    Log.d(TAG, "onBindViewHolder: ${e.message}")
                }


            }
        }
    }


    inner class HoursViewHolder(val binding: CustomFavListBinding) :
        RecyclerView.ViewHolder(binding.root)

}