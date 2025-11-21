package com.example.carkharidlo.ui.checkout

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.carkharidlo.R.*
import com.example.carkharidlo.data.CartRepository

class Checkout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_checkout)

        val tvTotal = findViewById<TextView>(id.tv_total_amounts)
        val tvMessage = findViewById<TextView>(id.tv_order_messages)
        val btnPlaceOrder = findViewById<Button>(id.btn_place_orders)

        val totalPrice = CartRepository.totalPrice()
        tvTotal.text = "Total Amount: ₹ $totalPrice"

        btnPlaceOrder.setOnClickListener {
            tvMessage.text = "🎉 Order Placed Successfully!\nOur dealer will contact you shortly."
            CartRepository.clearCart()
        }
    }
}
