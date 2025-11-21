package com.example.carkharidlo.ui.Cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.carkharidlo.data.CartItem
import com.example.carkharidlo.data.CartRepository

class CartViewModel : ViewModel() {

    val cartItems: LiveData<MutableList<CartItem>> = CartRepository.items

    fun addToCart(item: CartItem) {
        CartRepository.addToCart(item)
    }

    fun increaseQuantity(id: String) {
        CartRepository.increaseQty(id)
    }

    fun decreaseQuantity(id: String) {
        CartRepository.decreaseQty(id)
    }

    fun removeFromCart(id: String) {
        CartRepository.removeFromCart(id)
    }

    fun clearCart() {
        CartRepository.clearCart()
    }

    fun totalPrice(): Long {
        return CartRepository.totalPrice()
    }
}
