package com.example.carkharidlo.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carkharidlo.R
import com.example.carkharidlo.model.SignupRequest
import com.example.carkharidlo.model.SignupResponse
import com.example.carkharidlo.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Sign_Up_Activity : AppCompatActivity() {

    private lateinit var nameBox: EditText
    private lateinit var emailBox: EditText
    private lateinit var passwordBox: EditText
    private lateinit var confirmPasswordBox: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var signUpButton: Button
    private lateinit var signInText: TextView
    private lateinit var termsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        nameBox = findViewById(R.id.signup_name_box)
        emailBox = findViewById(R.id.signin_email_box)
        passwordBox = findViewById(R.id.signin_password_box)
        confirmPasswordBox = findViewById(R.id.confirm_password_box)
        checkBox = findViewById(R.id.signup_checkbox)
        signUpButton = findViewById(R.id.sign_in_button)
        signInText = findViewById(R.id.bottom_text_02)
        termsText = findViewById(R.id.signin_remember_me)

        termsText.setOnClickListener {
            showTermsDialog()
        }

        signUpButton.setOnClickListener {

            val name = nameBox.text.toString().trim()
            val email = emailBox.text.toString().trim()
            val password = passwordBox.text.toString().trim()
            val confirmPassword = confirmPasswordBox.text.toString().trim()

            when {

                name.isEmpty() ||
                        email.isEmpty() ||
                        password.isEmpty() ||
                        confirmPassword.isEmpty() -> {

                    Toast.makeText(
                        this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                password != confirmPassword -> {

                    Toast.makeText(
                        this,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !checkBox.isChecked -> {

                    Toast.makeText(
                        this,
                        "Please accept Terms & Conditions",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {

                    signupUser(name, email, password)

                }
            }

        }

        signInText.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    Sign_In_Activity::class.java
                )
            )

            finish()
        }
    }

    private fun signupUser(
        name: String,
        email: String,
        password: String
    ) {

        val request = SignupRequest(
            name,
            email,
            password
        )

        RetrofitClient
            .apiService
            .signupUser(request)
            .enqueue(object : Callback<SignupResponse> {

                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@Sign_Up_Activity,
                            response.body()?.message ?: "Signup Successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(
                            Intent(
                                this@Sign_Up_Activity,
                                Sign_In_Activity::class.java
                            )
                        )

                        finish()

                    } else {

                        Toast.makeText(
                            this@Sign_Up_Activity,
                            "Signup Failed",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }

                override fun onFailure(
                    call: Call<SignupResponse>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        this@Sign_Up_Activity,
                        t.message ?: "Network Error",
                        Toast.LENGTH_LONG
                    ).show()

                }

            })

    }

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

            .setNegativeButton("Close") { dialog, _ ->

                dialog.dismiss()

            }

            .show()
    }
}