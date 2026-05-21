package com.example.carkharidlo.data

data class CartItem(
    val id: Int,
    val user_id: Int,
    val car_id: Int,
    val car_name: String,
    val car_price: String,
    val car_image: String,
    var quantity: Int
)