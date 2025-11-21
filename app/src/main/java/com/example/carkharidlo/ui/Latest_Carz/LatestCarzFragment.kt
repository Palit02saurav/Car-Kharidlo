package com.example.carkharidlo.ui.Latest_Carz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carkharidlo.databinding.FragmentLatestCarzBinding
import com.example.carkharidlo.ui.CarDetails.*

class LatestCarzFragment : Fragment() {

    private var _binding: FragmentLatestCarzBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLatestCarzBinding.inflate(inflater, container, false)

        binding.cardLatest1.setOnClickListener {
            startActivity(Intent(requireContext(), Honda_City::class.java))
        }

        binding.cardLatest2.setOnClickListener {
            startActivity(Intent(requireContext(), Maruti_Swift::class.java))
        }

        binding.cardLatest3.setOnClickListener {
            startActivity(Intent(requireContext(), Hyundai_I20::class.java))
        }

        binding.cardLatest4.setOnClickListener {
            startActivity(Intent(requireContext(), Tata_Nexon::class.java))
        }

        binding.cardLatest5.setOnClickListener {
            startActivity(Intent(requireContext(), Maruti_Baleno::class.java))
        }

        binding.cardLatest6.setOnClickListener {
            startActivity(Intent(requireContext(), Kia_Sonnet::class.java))
        }

        binding.cardLatest7.setOnClickListener {
            startActivity(Intent(requireContext(), Volkswagon_virtus::class.java))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
