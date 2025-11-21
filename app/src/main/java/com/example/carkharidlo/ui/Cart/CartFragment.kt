package com.example.carkharidlo.ui.Cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carkharidlo.data.CartItem
import com.example.carkharidlo.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CartAdapter
    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        adapter = CartAdapter()

        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCart.adapter = adapter

        viewModel.cartItems.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            updateTotal(list)
        }

        binding.buttonCheckout.setOnClickListener {
        }

        return binding.root
    }

    private fun updateTotal(list: List<CartItem>) {
        val total = list.sumOf { it.price * it.quantity }
        binding.textTotalPrice.text = "Total: ₹ $total"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
