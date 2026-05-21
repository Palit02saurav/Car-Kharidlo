package com.example.carkharidlo.ui.Profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carkharidlo.R
import com.example.carkharidlo.utils.ImageUtils
import java.io.File

class PersonalInfoActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var sharedPrefs: SharedPreferences

    private val PICK_IMAGE_REQUEST = 1001
    private var selectedImageUri: Uri? = null
    private var savedImagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        profileImage = findViewById(R.id.edit_profile_image)
        nameEditText = findViewById(R.id.edit_name)
        emailEditText = findViewById(R.id.edit_email)
        saveButton = findViewById(R.id.save_button)

        sharedPrefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        nameEditText.setText(sharedPrefs.getString("name", "Guest User"))
        emailEditText.setText(sharedPrefs.getString("email", "guest@example.com"))

        savedImagePath = sharedPrefs.getString("profileImagePath", null)

        if (!savedImagePath.isNullOrEmpty()) {
            val file = File(savedImagePath!!)
            if (file.exists()) {
                profileImage.setImageURI(Uri.fromFile(file))
            }
        }

        profileImage.setOnClickListener {
            openGallery()
        }

        saveButton.setOnClickListener {
            saveUserData()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            profileImage.setImageURI(selectedImageUri)
        }
    }

    private fun saveUserData() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val editor = sharedPrefs.edit()
        editor.putString("name", name)
        editor.putString("email", email)

        selectedImageUri?.let {
            val path = ImageUtils.saveProfileImage(this, it)
            editor.putString("profileImagePath", path)
        }

        editor.commit()

        Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
        finish()
    }
}