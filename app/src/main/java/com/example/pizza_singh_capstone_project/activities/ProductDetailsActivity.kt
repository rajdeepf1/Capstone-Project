package com.example.pizza_singh_capstone_project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.pizza_singh_capstone_project.databinding.ActivityProductDetailsBinding
import com.example.pizza_singh_capstone_project.databinding.ActivityProductListBinding

class ProductDetailsActivity : AppCompatActivity() {
    private val TAG: String? = "ProductListActivity"
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var productName : String
    private lateinit var productId: String
    private lateinit var productPrice: String
    private lateinit var productImage: String
    private lateinit var productDescription: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        productName = intent.getStringExtra("productName").toString()
        productId = intent.getStringExtra("productId").toString()
        productPrice = intent.getStringExtra("productPrice").toString()
        productImage = intent.getStringExtra("productImage").toString()
        productDescription = intent.getStringArrayListExtra("productDescription") as ArrayList<String>

        Glide.with(applicationContext)
            .load(productImage)
            .into(binding.imageView)

        binding.titleText.text = productName
        binding.priceText.text = "$ ${productPrice}"
        binding.descriptionText.text = productDescription[0]
        binding.longDescriptionText.text = productDescription[1]


    }
}