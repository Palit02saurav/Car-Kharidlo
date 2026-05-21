package com.example.carkharidlo.model

data class Car(
    val id: Int = 0,
    val name: String,
    val price: String,
    val drivenKm: String,
    val fuelType: String,
    val transmission: String,
    val category: String,
    val imagePath: String
)