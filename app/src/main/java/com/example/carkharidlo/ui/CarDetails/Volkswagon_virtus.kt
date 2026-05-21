package com.example.carkharidlo.ui.CarDetails

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carkharidlo.R
import com.example.carkharidlo.data.CartRepository

class Volkswagon_virtus : AppCompatActivity() {

    private val dealerNumber = "9876543210"
    private val whatsappNumber = "9876543210"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volkswagon_virtus)

        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        val btnFavorite = findViewById<ImageButton>(R.id.btn_favorite)
        val btnCall = findViewById<Button>(R.id.btn_call_dealer)
        val btnWhatsapp = findViewById<Button>(R.id.btn_whatsapp)
        val btnAddCart = findViewById<Button>(R.id.btn_add_cart)

        btnBack.setOnClickListener {
            finish()
        }

        btnFavorite.setOnClickListener {
            Toast.makeText(this, "Added to Wishlist ❤️", Toast.LENGTH_SHORT).show()
        }

        btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$dealerNumber")
            startActivity(intent)
        }

        btnWhatsapp.setOnClickListener {
            openWhatsappChat()
        }

        btnAddCart.setOnClickListener {
            CartRepository.addToCart(
                context = this,
                carId = 113,
                carName = "Volkswagen Virtus",
                carPrice = "1941000",
                carImage = "volkie",
                onSuccess = {
                    Toast.makeText(
                        this,
                        "Car added to cart ✔",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onError = {
                    Toast.makeText(
                        this,
                        "Failed to add cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun openWhatsappChat() {
        val message = "Hello! I want more details about Volkswagen Virtus."

        try {
            val uri = Uri.parse(
                "https://wa.me/$whatsappNumber/?text=${Uri.encode(message)}"
            )

            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.whatsapp")
            startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "WhatsApp is not installed on your phone",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}