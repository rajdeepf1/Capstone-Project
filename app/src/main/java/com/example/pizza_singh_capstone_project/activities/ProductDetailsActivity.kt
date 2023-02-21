package com.example.pizza_singh_capstone_project.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.databinding.ActivityProductDetailsBinding
import com.example.pizza_singh_capstone_project.databinding.ActivityProductListBinding
import com.example.pizza_singh_capstone_project.models.CartModel
import com.example.pizza_singh_capstone_project.models.LoginSignupModel
import com.example.pizza_singh_capstone_project.utils.Constant
import com.example.pizza_singh_capstone_project.utils.SharedPref
import com.example.pizza_singh_capstone_project.viewmodels.CartViewModel
import kotlin.properties.Delegates

class ProductDetailsActivity : AppCompatActivity() {
    private val TAG: String? = "ProductListActivity"
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var productName: String
    private lateinit var productId: String
    private lateinit var productPrice: String
    private lateinit var productImage: String
    private var isVeg by Delegates.notNull<Boolean>()
    private lateinit var productDescription: ArrayList<String>
    private lateinit var cartViewModel: CartViewModel


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
        isVeg = intent.getBooleanExtra("isVeg", false)
        productDescription =
            intent.getStringArrayListExtra("productDescription") as ArrayList<String>

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
        } else {
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


        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)


        binding.addToCartButton.setOnClickListener {

            var extraThings: ArrayList<String> = ArrayList()


            // Getting the checked radio button id
            // from the radio group
            val selectedOption: Int = binding.radioGroup!!.checkedRadioButtonId

            // Assigning id of the checked radio button
            val radioButton: RadioButton = findViewById(selectedOption)

            // radio button value
            val radioText: String = radioButton.text.toString()

            if (!radioText.isNullOrBlank()) {
                extraThings.add(radioText)
            }

            // userId
            val loginSignupModel: LoginSignupModel = SharedPref.getUserObject(applicationContext)

            if (loginSignupModel.userId != 0L) {

                if (binding.cokeCheckBox.isChecked) {
                    extraThings.add(binding.cokeCheckBox.text.toString())
                }
                if (binding.chocoLavaCakeCheckBox.isChecked) {
                    extraThings.add(binding.chocoLavaCakeCheckBox.text.toString())
                }
                if (binding.chocolateOverloadBrownieCheckBox.isChecked) {
                    extraThings.add(binding.chocolateOverloadBrownieCheckBox.text.toString())
                }
                if (binding.paneerCubesCheckBox.isChecked) {
                    extraThings.add(binding.paneerCubesCheckBox.text.toString())
                }
                if (binding.mushroomsCheckBox.isChecked) {
                    extraThings.add(binding.mushroomsCheckBox.text.toString())
                }
                if (binding.blackOlivesCheckBox.isChecked) {
                    extraThings.add(binding.blackOlivesCheckBox.text.toString())
                }
                if (binding.spicyJalapenosCheckBox.isChecked) {
                    extraThings.add(binding.spicyJalapenosCheckBox.text.toString())
                }
                if (binding.redPaprikaCheckBox.isChecked) {
                    extraThings.add(binding.redPaprikaCheckBox.text.toString())
                }
                if (binding.goldenCornCheckBox.isChecked) {
                    extraThings.add(binding.goldenCornCheckBox.text.toString())
                }

                val commaSeperatedString = extraThings.joinToString { it -> "\'${it}\'" }

                Log.d(TAG, "onCreate: ${commaSeperatedString}")

                cartViewModel.insert(
                    applicationContext,
                    CartModel(
                        productName,
                        productId,
                        productPrice,
                        productImage,
                        isVeg,
                        loginSignupModel.userId.toString(),
                        commaSeperatedString
                    )
                )

                Constant.showToast(applicationContext,"Items Added To Cart!")


            }else{
                startActivity(Intent(this,MainActivity::class.java).putExtra("isCommingFromDifferentScreen",true))
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}