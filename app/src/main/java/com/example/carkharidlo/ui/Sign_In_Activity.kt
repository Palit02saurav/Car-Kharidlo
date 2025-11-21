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

        // Already logged in?
        val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Normal Sign In
        signInBtn.setOnClickListener {
            val email = emailBox.text.toString().trim()
            val password = passwordBox.text.toString().trim()

            val savedEmail = sharedPrefs.getString("email", null)
            val savedPassword = sharedPrefs.getString("password", null)

            when {
                email.isEmpty() || password.isEmpty() ->
                    Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                email != savedEmail || password != savedPassword ->
                    Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show()
                else -> {
                    sharedPrefs.edit().putBoolean("isLoggedIn", true).apply()
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        // Sign Up Page navigation
        signUpText.setOnClickListener {
            startActivity(Intent(this, Sign_Up_Activity::class.java))
        }

        // Forgot Password redirects to Sign Up
        forgotPassword.setOnClickListener {
            startActivity(Intent(this, Sign_Up_Activity::class.java))
        }

        // Facebook / Google Auto Sign In
        val socialLogin = { platform: String ->
            val defaultEmail = "$platform@example.com"
            val defaultName = "$platform User"
            val defaultPassword = "0000"

            val editor = sharedPrefs.edit()
            editor.putString("email", defaultEmail)
            editor.putString("name", defaultName)
            editor.putString("password", defaultPassword)
            editor.putBoolean("isLoggedIn", true)
            editor.apply()

            Toast.makeText(this, "Auto signed in with $platform\nPassword is 0000 😎", Toast.LENGTH_LONG).show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        facebookBtn.setOnClickListener { socialLogin("Facebook") }
        googleBtn.setOnClickListener { socialLogin("Google") }
    }
}
