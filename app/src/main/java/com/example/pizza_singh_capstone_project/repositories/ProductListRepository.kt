package com.example.pizza_singh_capstone_project.repositories

import android.util.Log
import com.example.pizza_singh_capstone_project.interfaces.NetworkResult
import com.example.pizza_singh_capstone_project.models.CategoriesModel
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.models.ProductModel
import com.example.pizza_singh_capstone_project.utils.Collections
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.logging.Handler

class ProductListRepository(private val category_name: String, private var category_id: String) {
    private val TAG: String? = "ProductListRepository"
    val firestore = FirebaseFirestore.getInstance()

    suspend fun getProducts(): List<ProductModel> {
        var list: List<ProductModel>? = ArrayList()

        var collectionName: Collections? = null

        if (category_name.equals("Big Veg")) {
            collectionName = Collections.bigVeg
        } else if (category_name.equals("Big Non Veg")) {
            collectionName = Collections.bigNonVeg
        } else if (category_name.equals("Regular Veg")) {
            collectionName = Collections.regularVeg
        } else if (category_name.equals("Regular Non Veg")) {
            collectionName = Collections.regularNonVeg
        }

        val uidRef: DocumentReference = firestore.collection("Categories").document(category_id)
        uidRef.collection("${collectionName}").get().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "getProducts: ${it.result.documents}")
                it.result.documents.map {
                    val productId: String = it.id
                    val productDescription: ArrayList<String> =
                        it.data?.getValue("productDescription") as ArrayList<String>
                    val productImage: String = it.data?.getValue("productImage").toString()
                    val productName: String = it.data?.getValue("productName").toString()
                    val productPrice: String = it.data?.getValue("productPrice").toString()
                    var isVeg: Boolean = false
                    isVeg = collectionName!!.equals(Collections.bigVeg)|| collectionName!!.equals(Collections.regularVeg)
                    (list as ArrayList<ProductModel>).add(
                        ProductModel(
                            productId = productId,
                            productDescription = productDescription,
                            productImage = productImage,
                            productName = productName,
                            productPrice = productPrice,
                            isVeg = isVeg
                        )
                    )
                }
            }else{
                NetworkResult.Error("No Data Found!",null)
            }

        }.addOnFailureListener {
            NetworkResult.Error(it.message.toString(), null)
        }.await()
        delay(2000)
        return list!!
    }


}