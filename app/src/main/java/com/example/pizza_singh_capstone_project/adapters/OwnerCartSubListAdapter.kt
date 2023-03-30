package com.example.pizza_singh_capstone_project.adapters

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.activities.MainActivity
import com.example.pizza_singh_capstone_project.databinding.CustomOrderListBinding
import com.example.pizza_singh_capstone_project.databinding.CustomOwnerSubOrderListBinding
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.models.OrdersModel
import com.example.pizza_singh_capstone_project.models.OwnerCartModel
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.Constant.CHANNEL_ID
import com.example.pizza_singh_capstone_project.utils.Constant.CHANNEL_NAME
import com.example.pizza_singh_capstone_project.utils.Constant.NOTIF_ID
import com.example.pizza_singh_capstone_project.utils.Coroutines
import com.example.pizza_singh_capstone_project.utils.SharedPref
import com.example.pizza_singh_capstone_project.viewmodels.CartViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import dev.shreyaspatil.MaterialDialog.MaterialDialog
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList


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
//            with(list[position]) {
//
//
//            }
            try {
                val model = list[position]
                Log.d(TAG, "onBindViewHolder: ${model.productName}")
            }catch (e:Exception){
                Log.d(TAG, "onBindViewHolder: ${e.message}")
            }
           
//            binding.textViewName.text = model.productName
//            binding.textViewDescription.text = model.extraThings
//            Glide.with(holder.itemView.context)
//                .load(model.productImage)
//                .into(binding.imageView)
//            if (model.isVeg) {
//                binding.textViewName.setCompoundDrawablesWithIntrinsicBounds(
//                    R.drawable.veg,
//                    0,
//                    0,
//                    0
//                )
//            } else {
//                binding.textViewName.setCompoundDrawablesWithIntrinsicBounds(
//                    R.drawable.nonveg,
//                    0,
//                    0,
//                    0
//                )
//            }
        }
    }

    inner class HoursViewHolder(val binding: CustomOwnerSubOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)

}