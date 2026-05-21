package com.example.carkharidlo.ui.SellCar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.carkharidlo.database.CarDatabaseHelper
import com.example.carkharidlo.databinding.FragmentSellCarBinding
import com.example.carkharidlo.model.Car
import com.example.carkharidlo.utils.ImageUtils

class SellCarFragment : Fragment() {

    private var _binding: FragmentSellCarBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: CarDatabaseHelper
    private var selectedImageUri: Uri? = null

    private val PICK_IMAGE_REQUEST = 2001

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellCarBinding.inflate(inflater, container, false)

        dbHelper = CarDatabaseHelper(requireContext())

        setupSpinner()

        binding.carImage.setOnClickListener {
            openGallery()
        }

        binding.btnListCar.setOnClickListener {
            saveCar()
        }

        return binding.root
    }

    private fun setupSpinner() {
        val categories = arrayOf("Latest", "Refurbished")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        binding.spinnerCategory.adapter = adapter
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            binding.carImage.setImageURI(selectedImageUri)
        }
    }

    private fun saveCar() {
        val name = binding.etCarName.text.toString().trim()
        val price = binding.etPrice.text.toString().trim()
        val driven = binding.etDriven.text.toString().trim()
        val fuel = binding.etFuel.text.toString().trim()
        val transmission = binding.etTransmission.text.toString().trim()
        val category = binding.spinnerCategory.selectedItem.toString()

        if (
            name.isEmpty() ||
            price.isEmpty() ||
            driven.isEmpty() ||
            fuel.isEmpty() ||
            transmission.isEmpty() ||
            selectedImageUri == null
        ) {
            Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val imagePath = ImageUtils.saveCarImage(requireContext(), selectedImageUri!!)

        val car = Car(
            name = name,
            price = price,
            drivenKm = driven,
            fuelType = fuel,
            transmission = transmission,
            category = category,
            imagePath = imagePath
        )

        val inserted = dbHelper.insertCar(car)

        if (inserted) {
            Toast.makeText(requireContext(), "Car listed successfully", Toast.LENGTH_SHORT).show()

            binding.etCarName.text?.clear()
            binding.etPrice.text?.clear()
            binding.etDriven.text?.clear()
            binding.etFuel.text?.clear()
            binding.etTransmission.text?.clear()
            binding.carImage.setImageResource(android.R.drawable.ic_menu_camera)
            selectedImageUri = null
        } else {
            Toast.makeText(requireContext(), "Failed to save", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}