package com.example.carkharidlo.ui.CarDetails

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.carkharidlo.data.CartItem
import com.example.carkharidlo.data.CartRepository
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carkharidlo.R

class MG_Hector : AppCompatActivity() {

    private val dealerNumber = "9876543210"
    private val whatsappNumber = "9876543210"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mg_hector)

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

        CartRepository.addToCart(
            CartItem(
                id = "honda_city_2023",
                name = "Honda City 2023",
                price = 1250000L,
                quantity = 1,
                imageRes = R.drawable.hector
            )
        )
        Toast.makeText(this, "Car added to cart ✔", Toast.LENGTH_SHORT).show()

    }

    private fun openWhatsappChat() {
        val message = "Hello! I want more details about MG Hector."

        try {
            val uri = Uri.parse("https://wa.me/$whatsappNumber/?text=${Uri.encode(message)}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.whatsapp")
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "WhatsApp is not installed on your phone", Toast.LENGTH_LONG).show()
        }
    }
}
