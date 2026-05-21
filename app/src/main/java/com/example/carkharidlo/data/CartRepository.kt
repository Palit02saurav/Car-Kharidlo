package com.example.carkharidlo.data

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carkharidlo.api.AddCartRequest
import com.example.carkharidlo.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CartRepository {

    private val _items = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val items: LiveData<MutableList<CartItem>> = _items

    private fun getUserId(context: Context): Int {
        val prefs: SharedPreferences =
            context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        return prefs.getInt("userId", 0)
    }

    fun fetchCart(context: Context) {
        val userId = getUserId(context)

        RetrofitClient.apiService.getCart(userId)
            .enqueue(object : Callback<com.example.carkharidlo.api.CartResponse> {
                override fun onResponse(
                    call: Call<com.example.carkharidlo.api.CartResponse>,
                    response: Response<com.example.carkharidlo.api.CartResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        _items.value = response.body()!!.cart.toMutableList()
                    }
                }

                override fun onFailure(
                    call: Call<com.example.carkharidlo.api.CartResponse>,
                    t: Throwable
                ) {
                }
            })
    }

    fun addToCart(
        context: Context,
        carId: Int,
        carName: String,
        carPrice: String,
        carImage: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val userId = getUserId(context)

        val request = AddCartRequest(
            user_id = userId,
            car_id = carId,
            car_name = carName,
            car_price = carPrice,
            car_image = carImage
        )

        RetrofitClient.apiService.addToCart(request)
            .enqueue(object : Callback<com.example.carkharidlo.api.ApiMessageResponse> {
                override fun onResponse(
                    call: Call<com.example.carkharidlo.api.ApiMessageResponse>,
                    response: Response<com.example.carkharidlo.api.ApiMessageResponse>
                ) {
                    if (response.isSuccessful) {
                        fetchCart(context)
                        onSuccess()
                    } else {
                        onError()
                    }
                }

                override fun onFailure(
                    call: Call<com.example.carkharidlo.api.ApiMessageResponse>,
                    t: Throwable
                ) {
                    onError()
                }
            })
    }

    fun removeFromCart(context: Context, cartId: Int) {
        RetrofitClient.apiService.removeCart(cartId)
            .enqueue(object : Callback<com.example.carkharidlo.api.ApiMessageResponse> {
                override fun onResponse(
                    call: Call<com.example.carkharidlo.api.ApiMessageResponse>,
                    response: Response<com.example.carkharidlo.api.ApiMessageResponse>
                ) {
                    fetchCart(context)
                }

                override fun onFailure(
                    call: Call<com.example.carkharidlo.api.ApiMessageResponse>,
                    t: Throwable
                ) {
                }
            })
    }

    fun clearCart(context: Context) {
        val userId = getUserId(context)

        RetrofitClient.apiService.clearCart(userId)
            .enqueue(object : Callback<com.example.carkharidlo.api.ApiMessageResponse> {
                override fun onResponse(
                    call: Call<com.example.carkharidlo.api.ApiMessageResponse>,
                    response: Response<com.example.carkharidlo.api.ApiMessageResponse>
                ) {
                    fetchCart(context)
                }

                override fun onFailure(
                    call: Call<com.example.carkharidlo.api.ApiMessageResponse>,
                    t: Throwable
                ) {
                }
            })
    }

    fun totalPrice(): Long {
        val list = _items.value ?: return 0

        return list.sumOf {
            val cleanPrice = it.car_price.replace("[^0-9]".toRegex(), "")
            val price = cleanPrice.toLongOrNull() ?: 0L
            price * it.quantity
        }
    }
}