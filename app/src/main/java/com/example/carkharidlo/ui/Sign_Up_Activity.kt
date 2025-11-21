package com.example.carkharidlo.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carkharidlo.R

class Sign_Up_Activity : AppCompatActivity() {

    private lateinit var emailBox: EditText
    private lateinit var passwordBox: EditText
    private lateinit var confirmPasswordBox: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var signUpButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var signInText: TextView
    private lateinit var termsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        emailBox = findViewById(R.id.signin_email_box)
        passwordBox = findViewById(R.id.signin_password_box)
        confirmPasswordBox = findViewById(R.id.confirm_password_box)
        checkBox = findViewById(R.id.signup_checkbox)
        signUpButton = findViewById(R.id.sign_in_button)
        signInText = findViewById(R.id.bottom_text_02)
        termsText = findViewById(R.id.signin_remember_me)

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        // ✅ Always works — click listener registered on every load
        termsText.setOnClickListener {
            showTermsDialog()
        }

        // Handle sign-up button click
        signUpButton.setOnClickListener {
            val email = emailBox.text.toString().trim()
            val password = passwordBox.text.toString().trim()
            val confirmPassword = confirmPasswordBox.text.toString().trim()

            when {
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ->
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()

                password != confirmPassword ->
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()

                !checkBox.isChecked ->
                    Toast.makeText(this, "Please accept Terms & Conditions", Toast.LENGTH_SHORT).show()

                else -> {
                    val editor = sharedPreferences.edit()
                    editor.putString("email", email)
                    editor.putString("password", password)
                    editor.apply()

                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, Sign_In_Activity::class.java))
                    finish()
                }
            }
        }

        // Go back to sign-in page
        signInText.setOnClickListener {
            startActivity(Intent(this, Sign_In_Activity::class.java))
            finish()
        }
    }

    // ✅ Independent function — always shows popup when called
    private fun showTermsDialog() {
        val termsMessage = """
            📝 Terms & Conditions

            1. Your personal data is used only for login purposes.
            2. Do not share credentials with anyone.
            3. CarKharidlo reserves the right to update terms anytime.
            4. Prices shown in the app are indicative and may vary.
            5. By signing up, you agree to receive occasional updates.

            🚗 Thank you for choosing CarKharidlo!
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Terms and Conditions")
            .setMessage(termsMessage)
            .setCancelable(true)
            .setPositiveButton("I Agree") { dialog, _ ->
                checkBox.isChecked = true
                dialog.dismiss()
            }
            .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
