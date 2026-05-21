package com.example.carkharidlo.ui.Cart

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carkharidlo.R
import com.example.carkharidlo.data.CartItem
import com.example.carkharidlo.databinding.ItemCartBinding
import java.io.File

class CartAdapter(
    private val onRemove: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

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

        val file = File(item.car_image)

        if (file.exists()) {
            holder.binding.imgItems.setImageURI(Uri.fromFile(file))
        } else {
            val imageRes = holder.itemView.context.resources.getIdentifier(
                item.car_image,
                "drawable",
                holder.itemView.context.packageName
            )

            if (imageRes != 0) {
                holder.binding.imgItems.setImageResource(imageRes)
            } else {
                holder.binding.imgItems.setImageResource(R.drawable.honda_city)
            }
        }

        holder.binding.tvNames.text = item.car_name
        holder.binding.tvPrices.text = item.car_price
        holder.binding.tvQtys.text = item.quantity.toString()

        holder.binding.btnIncreasess.isEnabled = false
        holder.binding.btnDecreases.isEnabled = false

        holder.binding.btnRemoves.setOnClickListener {
            onRemove(item.id)
        }
    }

    override fun getItemCount(): Int = items.size
}