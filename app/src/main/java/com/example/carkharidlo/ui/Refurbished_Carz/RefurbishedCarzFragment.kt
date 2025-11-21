package com.example.carkharidlo.ui.Refurbished_Carz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.carkharidlo.data.CartRepository
import com.example.carkharidlo.data.CartItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carkharidlo.databinding.FragmentRefurbishedCarzBinding
import com.example.carkharidlo.ui.CarDetails.*

class RefurbishedCarzFragment : Fragment() {

    private var _binding: FragmentRefurbishedCarzBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRefurbishedCarzBinding.inflate(inflater, container, false)

        binding.cardRefurbished1.setOnClickListener {
            startActivity(Intent(requireContext(), Honda_City::class.java))
        }

        binding.cardRefurbished2.setOnClickListener {
            startActivity(Intent(requireContext(), Maruti_Swift::class.java))
        }

        binding.cardRefurbished3.setOnClickListener {
            startActivity(Intent(requireContext(), Hyundai_I20::class.java))
        }

        binding.cardRefurbished4.setOnClickListener {
            startActivity(Intent(requireContext(), Tata_Nexon::class.java))
        }

        binding.cardRefurbished5.setOnClickListener {
            startActivity(Intent(requireContext(), Maruti_Baleno::class.java))
        }

        binding.cardRefurbished6.setOnClickListener {
            startActivity(Intent(requireContext(), Kia_Sonnet::class.java))
        }

        binding.cardRefurbished7.setOnClickListener {
            startActivity(Intent(requireContext(), Toyota_Fortuner::class.java))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
