package com.example.carkharidlo.ui.Cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carkharidlo.data.CartItem
import com.example.carkharidlo.data.CartRepository
import com.example.carkharidlo.databinding.ItemCartBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var items: MutableList<CartItem> = mutableListOf()

    fun submitList(newList: List<CartItem>) {
        items = newList.toMutableList()
        notifyDataSetChanged()
    }

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]

        holder.binding.imgItems.setImageResource(item.imageRes)
        holder.binding.tvNames.text = item.name
        holder.binding.tvPrices.text = "₹ ${item.price}"
        holder.binding.tvQtys.text = item.quantity.toString()

        // Increase Qty
        holder.binding.btnIncreasess.setOnClickListener {
            CartRepository.increaseQty(item.id)
            notifyItemChanged(position)
        }

        // Decrease Qty
        holder.binding.btnDecreases.setOnClickListener {
            CartRepository.decreaseQty(item.id)
            notifyDataSetChanged() // item may be removed
        }

        // Remove Item
        holder.binding.btnRemoves.setOnClickListener {
            CartRepository.removeFromCart(item.id)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = items.size
}
