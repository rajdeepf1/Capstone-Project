package com.example.pizza_singh_capstone_project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.ActivityProductDetailsBinding
import com.example.pizza_singh_capstone_project.databinding.ActivityProductListBinding
import kotlin.properties.Delegates

class ProductDetailsActivity : AppCompatActivity() {
    private val TAG: String? = "ProductListActivity"
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var productName : String
    private lateinit var productId: String
    private lateinit var productPrice: String
    private lateinit var productImage: String
    private var isVeg by Delegates.notNull<Boolean>()
    private lateinit var productDescription: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        productName = intent.getStringExtra("productName").toString()
        productId = intent.getStringExtra("productId").toString()
        productPrice = intent.getStringExtra("productPrice").toString()
        productImage = intent.getStringExtra("productImage").toString()
        productImage = intent.getStringExtra("productImage").toString()
        isVeg = intent.getBooleanExtra("isVeg",false)
        productDescription = intent.getStringArrayListExtra("productDescription") as ArrayList<String>

        Glide.with(applicationContext)
            .load(productImage)
            .into(binding.imageView)

        binding.titleText.text = productName
        if (isVeg) {
            binding.titleText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.veg,
                0,
                0,
                0
            )
        }else {
            binding.titleText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.nonveg,
                0,
                0,
                0
            )
        }
        binding.priceText.text = "$ ${productPrice}"
        binding.descriptionText.text = productDescription[0]
        binding.longDescriptionText.text = productDescription[1]


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}