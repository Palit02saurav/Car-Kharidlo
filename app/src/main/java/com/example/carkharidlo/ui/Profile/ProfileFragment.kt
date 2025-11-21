package com.example.carkharidlo.ui.Profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carkharidlo.R
import com.example.carkharidlo.databinding.FragmentProfileBinding
import com.example.carkharidlo.ui.Sign_In_Activity
import com.example.carkharidlo.ui.listings.NewListing
import com.example.carkharidlo.ui.listings.PreviousListings
import java.io.File

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        sharedPrefs = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        loadUserData()
        setupClickListeners()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadUserData()
    }

    private fun loadUserData() {

        binding.profileName.text = sharedPrefs.getString("name", "Guest User")
        binding.profileEmail.text = sharedPrefs.getString("email", "guest@example.com")

        val savedImagePath = sharedPrefs.getString("profileImagePath", null)
        if (!savedImagePath.isNullOrEmpty()) {
            val file = File(savedImagePath)
            if (file.exists()) {
                binding.profileImage.setImageURI(Uri.fromFile(file))
                return
            }
        }

        binding.profileImage.setImageResource(R.drawable.avaar)
    }

    private fun setupClickListeners() {

        binding.rowPersonalInfo.setOnClickListener {
            startActivity(Intent(requireContext(), PersonalInfoActivity::class.java))
        }

        binding.rowMyListings.setOnClickListener {
            val options = arrayOf("New Listing", "Previous Listings")
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Choose Option")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> startActivity(Intent(requireContext(), NewListing::class.java))
                        1 -> startActivity(Intent(requireContext(), PreviousListings::class.java))
                    }
                }
                .show()
        }

        binding.rowLogout.setOnClickListener {
            sharedPrefs.edit().putBoolean("isLoggedIn", false).apply()
            startActivity(
                Intent(requireContext(), Sign_In_Activity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
