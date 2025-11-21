package com.example.carkharidlo.ui.listings

import android.net.Uri

data class ListingModel(
    val name: String,
    val price: String,
    val contact: String,
    val email: String,
    val address: String,
    val state: String,
    val city: String,
    val imageUri: Uri
)
