package com.example.carkharidlo.ui.checkout

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carkharidlo.R
import com.example.carkharidlo.data.CartItem
import com.example.carkharidlo.databinding.ItemCartBinding
import java.io.File

class CheckoutAdapter(
    private val items: List<CartItem>
) : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
        holder.binding.tvPrices.text = "₹ ${item.car_price}"
        holder.binding.tvQtys.text = item.quantity.toString()

        holder.binding.btnIncreasess.visibility = View.GONE
        holder.binding.btnDecreases.visibility = View.GONE
        holder.binding.btnRemoves.visibility = View.GONE
    }

    override fun getItemCount(): Int = items.size
}