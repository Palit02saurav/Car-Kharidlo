package com.example.carkharidlo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object CartRepository {

    private val _items = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val items: LiveData<MutableList<CartItem>> = _items

    fun addToCart(item: CartItem) {
        val list = _items.value ?: mutableListOf()
        val existing = list.find { it.id == item.id }

        if (existing != null) {
            existing.quantity++
        } else {
            list.add(item)
        }

        _items.value = list
    }

    fun increaseQty(id: String) {
        val list = _items.value ?: return
        val item = list.find { it.id == id }
        item?.let {
            it.quantity++
            _items.value = list
        }
    }

    fun decreaseQty(id: String) {
        val list = _items.value ?: return
        val item = list.find { it.id == id }

        item?.let {
            if (it.quantity > 1) {
                it.quantity--
            } else {
                list.remove(it)
            }
            _items.value = list
        }
    }

    // ✅ Added to support CartViewModel.updateQuantity()
    fun updateQuantity(id: String, qty: Int) {
        val list = _items.value ?: return
        val item = list.find { it.id == id }

        item?.let {
            if (qty <= 0) {
                list.remove(it)
            } else {
                it.quantity = qty
            }
            _items.value = list
        }
    }

    // ✅ Added to support CartViewModel.removeFromCart()
    fun removeFromCart(id: String) {
        val list = _items.value ?: return
        list.removeAll { it.id == id }
        _items.value = list
    }

    fun removeItem(id: String) {
        val list = _items.value ?: return
        list.removeAll { it.id == id }
        _items.value = list
    }

    fun clearCart() {
        _items.value = mutableListOf()
    }

    fun totalPrice(): Long {
        val list = _items.value ?: return 0
        return list.sumOf { it.price * it.quantity }
    }
}
