package com.example.carkharidlo.data

data class CartItem(
    val id: String,
    val name: String,
    val price: Long,
    var quantity: Int,
    val imageRes: Int
)
