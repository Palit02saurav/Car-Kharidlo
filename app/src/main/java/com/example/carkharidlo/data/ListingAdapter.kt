package com.example.carkharidlo.ui.listings

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carkharidlo.data.ListingItem
import com.example.carkharidlo.databinding.ItemPreviousListingBinding

class ListingAdapter(private val list: List<ListingItem>) :
    RecyclerView.Adapter<ListingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPreviousListingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPreviousListingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.imgCar.setImageURI(Uri.parse(item.imageUri))
        holder.binding.tvCarName.text = item.carName
        holder.binding.tvPrice.text = "₹ ${item.price}"
        holder.binding.tvLocation.text = "${item.city}, ${item.state}"
    }
}
