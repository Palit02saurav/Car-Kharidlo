package com.example.carkharidlo.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carkharidlo.MainActivity
import com.example.carkharidlo.R
import com.example.carkharidlo.model.LoginRequest
import com.example.carkharidlo.model.LoginResponse
import com.example.carkharidlo.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Sign_In_Activity : AppCompatActivity() {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val emailBox = findViewById<EditText>(R.id.signin_email_box)
        val passwordBox = findViewById<EditText>(R.id.signin_password_box)
        val signInBtn = findViewById<Button>(R.id.sign_in_button)
        val signUpText = findViewById<TextView>(R.id.bottom_text_02)
        val forgotPassword = findViewById<TextView>(R.id.signin_forgot_password)
        val facebookBtn = findViewById<Button>(R.id.facebook_button)
        val googleBtn = findViewById<Button>(R.id.google_button)

        sharedPrefs = getSharedPreferences("UserData", MODE_PRIVATE)

        val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        signInBtn.setOnClickListener {

            val email = emailBox.text.toString().trim()
            val password = passwordBox.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = LoginRequest(email, password)

            RetrofitClient.apiService.loginUser(loginRequest)
                .enqueue(object : Callback<LoginResponse> {

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        if (response.isSuccessful && response.body() != null) {

                            val user = response.body()!!

                            sharedPrefs.edit()
                                .putBoolean("isLoggedIn", true)
                                .putString("email", user.email)
                                .putString("name", user.name)
                                .putInt("userId", user.userId)
                                .apply()

                            Toast.makeText(
                                this@Sign_In_Activity,
                                user.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(Intent(this@Sign_In_Activity, MainActivity::class.java))
                            finish()

                        } else {
                            Toast.makeText(
                                this@Sign_In_Activity,
                                "Invalid credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(
                            this@Sign_In_Activity,
                            "Connection Failed: ${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this, Sign_Up_Activity::class.java))
        }

        forgotPassword.setOnClickListener {
            Toast.makeText(this, "Forgot Password Coming Soon", Toast.LENGTH_SHORT).show()
        }

        facebookBtn.setOnClickListener {
            Toast.makeText(this, "Facebook Login Coming Soon", Toast.LENGTH_SHORT).show()
        }

        googleBtn.setOnClickListener {
            Toast.makeText(this, "Google Login Coming Soon", Toast.LENGTH_SHORT).show()
        }
    }
}