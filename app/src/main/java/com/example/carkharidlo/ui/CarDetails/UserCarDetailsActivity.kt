package com.example.carkharidlo.ui.CarDetails

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carkharidlo.data.CartRepository
import com.example.carkharidlo.databinding.ActivityUserCarDetailsBinding
import java.io.File

class UserCarDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserCarDetailsBinding
    private val dealerNumber = "9876543210"
    private val whatsappNumber = "9876543210"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserCarDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val carId = intent.getIntExtra("carId", 0)
        val carName = intent.getStringExtra("carName") ?: ""
        val carPrice = intent.getStringExtra("carPrice") ?: ""
        val drivenKm = intent.getStringExtra("drivenKm") ?: ""
        val fuelType = intent.getStringExtra("fuelType") ?: ""
        val transmission = intent.getStringExtra("transmission") ?: ""
        val imagePath = intent.getStringExtra("imagePath") ?: ""

        binding.tvCarName.text = carName
        binding.tvPrice.text = "₹$carPrice"
        binding.tvDriven.text = "$drivenKm km"
        binding.tvFuel.text = fuelType
        binding.tvTransmission.text = transmission

        val priceValue = carPrice.toLongOrNull() ?: 0
        binding.tvEmi.text = "₹${priceValue / 60}/mo"

        val file = File(imagePath)
        if (file.exists()) {
            binding.imgCar.setImageURI(Uri.fromFile(file))
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnFavorite.setOnClickListener {
            Toast.makeText(this, "Added to Wishlist ❤️", Toast.LENGTH_SHORT).show()
        }

        binding.btnCallDealer.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$dealerNumber")
            startActivity(intent)
        }

        binding.btnWhatsapp.setOnClickListener {
            openWhatsappChat(carName)
        }

        binding.btnAddCart.setOnClickListener {
            CartRepository.addToCart(
                context = this,
                carId = carId,
                carName = carName,
                carPrice = carPrice,
                carImage = imagePath,
                onSuccess = {
                    Toast.makeText(this, "Car added to cart ✔", Toast.LENGTH_SHORT).show()
                },
                onError = {
                    Toast.makeText(this, "Failed to add cart", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun openWhatsappChat(carName: String) {
        try {
            val uri = Uri.parse(
                "https://wa.me/$whatsappNumber/?text=${Uri.encode("Hello! I want details about $carName")}"
            )

            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.whatsapp")
            startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_LONG).show()
        }
    }
}