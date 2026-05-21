package com.example.carkharidlo.ui.checkout

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carkharidlo.R
import com.example.carkharidlo.data.CartRepository

class Checkout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val recycler = findViewById<RecyclerView>(R.id.recycler_checkout)
        val tvTotal = findViewById<TextView>(R.id.tv_total_amounts)
        val tvMessage = findViewById<TextView>(R.id.tv_order_messages)
        val btnPlaceOrder = findViewById<Button>(R.id.btn_place_orders)

        val cartItems = CartRepository.items.value ?: mutableListOf()

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = CheckoutAdapter(cartItems)

        val totalPrice = CartRepository.totalPrice()
        tvTotal.text = "Total Amount: ₹ $totalPrice"

        btnPlaceOrder.setOnClickListener {
            CartRepository.clearCart(this)

            tvMessage.text =
                "🎉 Order Placed Successfully!\nOur dealer will contact you shortly."

            Toast.makeText(this, "Order placed successfully", Toast.LENGTH_SHORT).show()
        }
    }
}