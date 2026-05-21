package com.example.carkharidlo.ui.Cart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.carkharidlo.data.CartItem
import com.example.carkharidlo.data.CartRepository

class CartViewModel : ViewModel() {

    val cartItems: LiveData<MutableList<CartItem>> = CartRepository.items

    fun fetchCart(context: Context) {
        CartRepository.fetchCart(context)
    }

    fun removeFromCart(context: Context, id: Int) {
        CartRepository.removeFromCart(context, id)
    }

    fun clearCart(context: Context) {
        CartRepository.clearCart(context)
    }

    fun totalPrice(): Long {
        return CartRepository.totalPrice()
    }
}