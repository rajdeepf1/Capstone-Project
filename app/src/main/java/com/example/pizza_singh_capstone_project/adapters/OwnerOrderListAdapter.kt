package com.example.pizza_singh_capstone_project.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.CustomOwnerOrderListBinding
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.*
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.Coroutines
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.reflect.typeOf

class OwnerOrderListAdapter(
    private var list: ArrayList<OwnerOrderModel>,
    private val context: Context
) :
    RecyclerView.Adapter<OwnerOrderListAdapter.HoursViewHolder>() {


    private val TAG: String? = OwnerOrderListAdapter::class.java.name

    private val viewPool = RecyclerView.RecycledViewPool()

    val firestore = FirebaseFirestore.getInstance()


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
                binding.textViewOrderId.setText("OrderId: "+orderId.toString())

                binding.textViewPrice.text = "Total "+totalAmount+" $"

                binding.imageViewAccept.setOnClickListener {
                    Coroutines.main {
                        val resp: Boolean = insertData(
                            OwnerOrderModel(
                                orderId,
                                ORDER_STATUS.accept,
                                orderList,
                                userId,
                                totalAmount
                            )
                        )

                        if (resp) {
                            Constant.showToast(context, "Order Accepted")
                            removeOrderData(this)
                        }

                    }
                }

                binding.imageViewReject.setOnClickListener {
                    Coroutines.main {
                        val resp: Boolean = insertData(
                            OwnerOrderModel(
                                orderId,
                                ORDER_STATUS.reject,
                                orderList,
                                userId,
                                totalAmount
                            )
                        )

                        if (resp) {
                            Constant.showToast(context, "Order Rejected")
                            removeOrderData(this)
                        }

                    }

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

    suspend fun insertData(data: OwnerOrderModel): Boolean {
        var resp = false
        firestore.collection("OrderHistory").add(data)
            .addOnCompleteListener(OnCompleteListener {
                resp = it.isSuccessful
            }).addOnFailureListener(OnFailureListener {
                resp = false
            }).await()
        return resp
    }

    suspend fun removeOrderData(data: OwnerOrderModel): Boolean {
        val resp = false
        firestore.collection("Orders")
            .whereEqualTo("orderId",data.orderId)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful ) {
                    it.result.documents.map {
                        Log.d(TAG, "removeOrderData: ${it.id}")
                        firestore.collection("Orders").document(it.id).delete()
                    }
                } else {
                    Log.d(TAG, "removeOrderData: Not deleted")
                }
            }.addOnFailureListener(OnFailureListener {
                Log.d(TAG, "getUserData: getUserDataAndCheckIfExists failure")
            }).await()
        return resp
    }

    inner class HoursViewHolder(val binding: CustomOwnerOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)

}