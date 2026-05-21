package com.example.carkharidlo.ui.Cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carkharidlo.databinding.FragmentCartBinding
import com.example.carkharidlo.ui.checkout.Checkout

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

        viewModel = ViewModelProvider(this)[CartViewModel::class.java]

        adapter = CartAdapter { cartId ->
            viewModel.removeFromCart(requireContext(), cartId)
            Toast.makeText(requireContext(), "Removed from cart", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCart.adapter = adapter

        viewModel.cartItems.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            updateTotal()
        }

        binding.buttonCheckout.setOnClickListener {
            startActivity(Intent(requireContext(), Checkout::class.java))
        }

        binding.buttonCheckout.setOnLongClickListener {
            viewModel.clearCart(requireContext())
            Toast.makeText(requireContext(), "Cart cleared", Toast.LENGTH_SHORT).show()
            true
        }

        viewModel.fetchCart(requireContext())

        return binding.root
    }

    private fun updateTotal() {
        val total = viewModel.totalPrice()
        binding.textTotalPrice.text = "Total: ₹ $total"
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchCart(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}