package com.example.carkharidlo.ui.listings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carkharidlo.databinding.ActivityNewListingBinding

class NewListing : AppCompatActivity() {

    private lateinit var binding: ActivityNewListingBinding
    private var selectedImageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()
    }

    private fun setupUi() {

        // 📸 Upload Image Button
        binding.btnUploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_CODE)
        }

        // ➕ Submit Listing
        binding.btnSubmitListing.setOnClickListener {

            val carName = binding.etCarName.text.toString().trim()
            val price = binding.etCarPrice.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val contact = binding.etContact.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val state = binding.etState.text.toString().trim()
            val city = binding.etCity.text.toString().trim()

            if (carName.isEmpty() || price.isEmpty() || email.isEmpty() ||
                contact.isEmpty() || address.isEmpty() ||
                state.isEmpty() || city.isEmpty() || selectedImageUri == null
            ) {
                Toast.makeText(this, "Please fill all fields and upload image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Listing Uploaded Successfully ✔", Toast.LENGTH_LONG).show()

            // TODO: SAVE TO DATABASE LATER
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            binding.imgCarPreview.setImageURI(selectedImageUri)
        }
    }
}
