package com.example.carkharidlo.ui.Recommended

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.carkharidlo.data.CartRepository
import com.example.carkharidlo.data.CartItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carkharidlo.databinding.FragmentRecommendedCarzBinding
import com.example.carkharidlo.ui.CarDetails.*

class RecommendedFragment : Fragment() {

    private var _binding: FragmentRecommendedCarzBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendedCarzBinding.inflate(inflater, container, false)

        binding.cardRecommended1.setOnClickListener {
            startActivity(Intent(requireContext(), Hyund_Creta::class.java))
        }

        binding.cardRecommended2.setOnClickListener {
            startActivity(Intent(requireContext(), Volkswagon_virtus::class.java))
        }

        binding.cardRecommended3.setOnClickListener {
            startActivity(Intent(requireContext(), Mahindra_XUV::class.java))
        }

        binding.cardRecommended4.setOnClickListener {
            startActivity(Intent(requireContext(), Tata_Harrier::class.java))
        }

        binding.cardRecommended5.setOnClickListener {
            startActivity(Intent(requireContext(), Kia_Sonnet::class.java))
        }

        binding.cardRecommended6.setOnClickListener {
            startActivity(Intent(requireContext(), MG_Hector::class.java))
        }

        binding.cardRecommended7.setOnClickListener {
            startActivity(Intent(requireContext(), Skoda_Kushaq::class.java))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
