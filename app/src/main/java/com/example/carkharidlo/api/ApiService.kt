package com.example.carkharidlo.api

import com.example.carkharidlo.data.CartItem
import com.example.carkharidlo.model.LoginRequest
import com.example.carkharidlo.model.LoginResponse
import com.example.carkharidlo.model.SignupRequest
import com.example.carkharidlo.model.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class AddCartRequest(
    val user_id: Int,
    val car_id: Int,
    val car_name: String,
    val car_price: String,
    val car_image: String
)

data class ApiMessageResponse(
    val success: Boolean,
    val message: String
)

data class CartResponse(
    val success: Boolean,
    val cart: List<CartItem>
)

interface ApiService {

    @POST("api/auth/signup")
    fun signupUser(
        @Body request: SignupRequest
    ): Call<SignupResponse>

    @POST("api/auth/login")
    fun loginUser(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("api/cart/add")
    fun addToCart(
        @Body request: AddCartRequest
    ): Call<ApiMessageResponse>

    @GET("api/cart/{userId}")
    fun getCart(
        @Path("userId") userId: Int
    ): Call<CartResponse>

    @DELETE("api/cart/remove/{id}")
    fun removeCart(
        @Path("id") id: Int
    ): Call<ApiMessageResponse>

    @DELETE("api/cart/clear/{userId}")
    fun clearCart(
        @Path("userId") userId: Int
    ): Call<ApiMessageResponse>
}