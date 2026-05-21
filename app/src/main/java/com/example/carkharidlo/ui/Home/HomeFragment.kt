package com.example.carkharidlo.ui.Home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carkharidlo.R
import com.example.carkharidlo.databinding.FragmentHomeBinding
import com.example.carkharidlo.model.SearchCar
import com.example.carkharidlo.ui.CarDetails.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())
    private var currentIndex = 0
    private val imageCount = 4
    private lateinit var allCars: List<SearchCar>

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val imageWidthDp = 340 + 20
            val imageWidthPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                imageWidthDp.toFloat(),
                resources.displayMetrics
            ).toInt()

            currentIndex = (currentIndex + 1) % imageCount
            binding.horizontalScrollView.smoothScrollTo(currentIndex * imageWidthPx, 0)
            handler.postDelayed(this, 3500)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupCars()
        setupRecycler()
        setupSearch()
        setupNavigation()
        setupPremiumInteractions()
        startAutoScroll()

        return binding.root
    }

    private fun setupCars() {
        allCars = listOf(
            SearchCar("Honda City", "₹ 15.5 Lakh", "Petrol • Automatic", R.drawable.honda_city, Honda_City::class.java),
            SearchCar("Maruti Swift", "₹ 8.5 Lakh", "Petrol • Manual", R.drawable.maruti_swift, Maruti_Swift::class.java),
            SearchCar("Hyundai i20", "₹ 10.2 Lakh", "Petrol • Automatic", R.drawable.hyundai_i20, Hyundai_I20::class.java),
            SearchCar("Tata Nexon", "₹ 15.5 Lakh", "Petrol • Automatic", R.drawable.tata_nex, Tata_Nexon::class.java),
            SearchCar("Maruti Baleno", "₹ 9.5 Lakh", "Petrol • Manual", R.drawable.maruti, Maruti_Baleno::class.java),
            SearchCar("Kia Sonnet", "₹ 14.5 Lakh", "Diesel • Automatic", R.drawable.kia_6_, Kia_Sonnet::class.java),
            SearchCar("Toyota Fortuner", "₹ 42 Lakh", "Diesel • Automatic", R.drawable.fortuner, Toyota_Fortuner::class.java),
            SearchCar("Volkswagen Virtus", "₹ 18 Lakh", "Petrol • Automatic", R.drawable.volky, Volkswagon_virtus::class.java),
            SearchCar("Hyundai Creta", "₹ 19 Lakh", "Petrol • Automatic", R.drawable.hyundai_creta, Hyund_Creta::class.java),
            SearchCar("Tata Harrier", "₹ 24 Lakh", "Diesel • Automatic", R.drawable.tata_nex, Tata_Harrier::class.java),
            SearchCar("Mahindra XUV", "₹ 21 Lakh", "Diesel • Automatic", R.drawable.mustang, Mahindra_XUV::class.java),
            SearchCar("MG Hector", "₹ 22 Lakh", "Petrol • Automatic", R.drawable.hector, MG_Hector::class.java)
        )
    }

    private fun setupRecycler() {
        binding.searchResultsRecycler.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun setupSearch() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()

                if (query.isEmpty()) {
                    binding.searchResultsRecycler.visibility = View.GONE
                    return
                }

                val filteredCars = allCars.filter {
                    it.name.lowercase().contains(query)
                }

                if (filteredCars.isNotEmpty()) {
                    binding.searchResultsRecycler.visibility = View.VISIBLE
                    binding.searchResultsRecycler.adapter =
                        SearchCarAdapter(filteredCars)
                } else {
                    binding.searchResultsRecycler.visibility = View.GONE
                }
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

    private fun setupNavigation() {

        binding.but01.setOnClickListener {
            animateClick(it)
            findNavController().navigate(R.id.latestCarzFragment)
        }

        binding.but02.setOnClickListener {
            animateClick(it)
            findNavController().navigate(R.id.refurbishedCarzFragment)
        }

        binding.butLatest.setOnClickListener {
            animateClick(it)
            findNavController().navigate(R.id.latestCarzFragment)
        }

        binding.topCar1.setOnClickListener {
            animateClick(it)
            startActivity(Intent(requireContext(), Hyund_Creta::class.java))
        }

        binding.topCar2.setOnClickListener {
            animateClick(it)
            startActivity(Intent(requireContext(), Volkswagon_virtus::class.java))
        }

        binding.featuredCard.setOnClickListener {
            animateClick(it)
            startActivity(Intent(requireContext(), Hyundai_I20::class.java))
        }
    }

    private fun setupPremiumInteractions() {
        binding.horizontalScrollView.setOnTouchListener { _, _ ->
            handler.removeCallbacks(autoScrollRunnable)
            handler.postDelayed(autoScrollRunnable, 5000)
            false
        }
    }

    private fun startAutoScroll() {
        handler.postDelayed(autoScrollRunnable, 3500)
    }

    private fun animateClick(view: View) {
        view.animate()
            .scaleX(0.96f)
            .scaleY(0.96f)
            .setDuration(100)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(120)
                    .start()
            }
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }
}