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
import com.example.pizza_singh_capstone_project.models.*
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.Constant.CHANNEL_ID
import com.example.pizza_singh_capstone_project.utils.Constant.CHANNEL_NAME
import com.example.pizza_singh_capstone_project.utils.Constant.NOTIF_ID
import com.example.pizza_singh_capstone_project.utils.Coroutines
import com.example.pizza_singh_capstone_project.utils.SharedPref
import com.example.pizza_singh_capstone_project.viewmodels.CartViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import dev.shreyaspatil.MaterialDialog.MaterialDialog
import kotlinx.coroutines.tasks.await
import java.util.*


class OrderListAdapter(
    private val list: List<CartModel>,
    private val context: Context,
    private val tvSubTotal: TextView,
    private val totalValueText: TextView,
    private val placeOrderButton: AppCompatButton,
    private val cartViewModel: CartViewModel,
    private val dialog: BottomSheetDialog,
) :
    RecyclerView.Adapter<OrderListAdapter.HoursViewHolder>() {

    private var TAG: String = "OrderListAdapter"
    var subTotalPrice: Double = 0.0
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val binding = CustomOrderListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        createNotifChannel()
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

                    subTotalPrice += String.format("%.2f", prodPrice * qyt).toDouble()
                    Log.d(TAG, "onBindViewHolder: ${subTotalPrice}")
                    tvSubTotal.text = String.format("%.2f", subTotalPrice)
                    val hstCalculation = subTotalPrice * 13 / 100
                    totalValueText.text = String.format("%.2f", subTotalPrice + hstCalculation)

                } catch (e: Exception) {
                    Log.d(TAG, "onBindViewHolder: ${e.message}")
                }

                placeOrderButton.setOnClickListener {
                    val loginSignupModel: LoginSignupModel = SharedPref.getUserObject(context)
                    val ordersModel: OrdersModel = OrdersModel(
                        orderId = Date().time,
                        orderList = list,
                        userId = loginSignupModel.userId,
                        totalAmount = totalValueText.text.toString()
                    )
                    Log.d(TAG, "onBindViewHolder: ${ordersModel}")
                    Coroutines.main {
                        val response: Boolean = insertData(ordersModel)
                        if (response) {

                            val mDialog = MaterialDialog.Builder(context as Activity)
                                .setAnimation(R.raw.checkmark)
                                .setTitle("Order Placed!")
                                .setMessage("Your order has been placed !")
                                .setCancelable(false)
                                .setPositiveButton(
                                    "Done"
                                ) { dialogInterface, which ->
                                    // Delete Operation
                                    Coroutines.main {
                                        cartViewModel.deleteAllCartData(context)
                                        dialogInterface.dismiss()
                                        dialog.dismiss()
                                        context.startActivity(
                                            Intent(context, MainActivity::class.java)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        )
                                    }

                                }
                                .setNegativeButton(
                                    "Add to favorite"
                                ) { dialogInterface, which ->
                                    Coroutines.main {
                                        val resp = insertFavoriteData(
                                            FavoriteModel(
                                                Date().time,
                                                loginSignupModel.userId,
                                                list
                                            )
                                        )
                                        if (resp) {
                                            Constant.showToast(
                                                context,
                                                "Your order has been added to favorite!"
                                            )
                                            cartViewModel.deleteAllCartData(context)
                                            dialogInterface.dismiss()
                                            dialog.dismiss()
                                            context.startActivity(
                                                Intent(context, MainActivity::class.java)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                            )
                                        } else {
                                            Constant.showToast(context, "Something went wrong!")
                                        }

                                    }
                                }
                                .build()

                            // Show Dialog
                            mDialog.show()


                            val intent = Intent(context, MainActivity::class.java)
                            val pendingIntent = TaskStackBuilder.create(context).run {
                                addNextIntentWithParentStack(intent)
                                getPendingIntent(0, PendingIntent.FLAG_MUTABLE)
                            }
                            val notifManger = NotificationManagerCompat.from(context)
                            val notif = NotificationCompat.Builder(context, CHANNEL_ID)
                                .setContentTitle("Sample Title")
                                .setContentText("This is sample body notif")
                                .setSmallIcon(R.mipmap.ic_app_icon_pizza_singh)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setContentIntent(pendingIntent)
                                .build()
                            if (ActivityCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return@main
                            }
                            notifManger.notify(NOTIF_ID, notif)

                        } else {
                            Constant.showToast(context, "Unable to place the order!")
                        }


                    }
                }

            }
        }
    }

    suspend fun insertData(data: OrdersModel): Boolean {
        var resp = false
        firestore.collection("Orders").add(data)
            .addOnCompleteListener {
                resp = it.isSuccessful
            }.addOnFailureListener {
                resp = false
            }.await()
        return resp
    }

    suspend fun getUser(userId: Long) {

        firestore.collection("User").whereEqualTo("userId", userId)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result.size() > 0) {
                        val userResponse =
                            it.result?.toObjects(LoginSignupModel::class.java)?.get(0)
                        Log.d(TAG, "getUser: ${userResponse}")
//                        for (document in it.result.documents) {
//                            Log.d(TAG, "${document.id} => ${document.data}")
//                        }
                    } else {
                        Log.d(TAG, "getUser: in else1")
                    }
                } else {
                    Log.d(TAG, "getUser: in else2")
                }
            }.addOnFailureListener(OnFailureListener {
                Log.d(TAG, "getUserData: getUserDataAndCheckIfExists failure")
                Log.d(TAG, "getUser: in fail ${it.message}")
            }).await()
    }

    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    suspend fun insertFavoriteData(data: FavoriteModel): Boolean {
        var resp = false
        firestore.collection("Favorites").add(data)
            .addOnCompleteListener(OnCompleteListener {
                resp = it.isSuccessful
            }).addOnFailureListener(OnFailureListener {
                resp = false
            }).await()
        return resp
    }

    inner class HoursViewHolder(val binding: CustomOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)

}