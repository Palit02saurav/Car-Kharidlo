package com.example.carkharidlo.ui.Refurbished_Carz

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carkharidlo.database.CarDatabaseHelper
import com.example.carkharidlo.databinding.FragmentRefurbishedCarzBinding
import com.example.carkharidlo.model.Car

class RefurbishedCarzFragment : Fragment() {

    private var _binding: FragmentRefurbishedCarzBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: CarDatabaseHelper
    private lateinit var adapter: RefurbishedCarAdapter
    private var allCars: List<Car> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRefurbishedCarzBinding.inflate(inflater, container, false)

        dbHelper = CarDatabaseHelper(requireContext())

        binding.recyclerRefurbishedCars.layoutManager =
            LinearLayoutManager(requireContext())

        loadCars()
        setupSearch()

        return binding.root
    }

    private fun loadCars() {
        allCars = dbHelper.getCarsByCategory("Refurbished")
        adapter = RefurbishedCarAdapter(allCars)
        binding.recyclerRefurbishedCars.adapter = adapter
    }

    private fun setupSearch() {
        binding.searchRefurbished.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()

                if (query.isEmpty()) {
                    adapter = RefurbishedCarAdapter(allCars)
                    binding.recyclerRefurbishedCars.adapter = adapter
                    return
                }

                val filteredCars = allCars.filter {
                    it.name.lowercase().contains(query) ||
                            it.fuelType.lowercase().contains(query) ||
                            it.transmission.lowercase().contains(query) ||
                            it.price.lowercase().contains(query)
                }

                adapter = RefurbishedCarAdapter(filteredCars)
                binding.recyclerRefurbishedCars.adapter = adapter
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadCars()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}