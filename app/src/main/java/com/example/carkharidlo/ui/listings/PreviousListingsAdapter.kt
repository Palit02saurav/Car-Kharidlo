package com.example.carkharidlo.ui.listings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carkharidlo.databinding.ItemPreviousListingBinding

class PreviousListingsAdapter :
    RecyclerView.Adapter<PreviousListingsAdapter.ViewHolder>() {

    private var items = ArrayList<ListingModel>()

    fun submitList(list: List<ListingModel>) {
        items = ArrayList(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPreviousListingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPreviousListingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.imgCar.setImageURI(item.imageUri)
        holder.binding.tvCarName.text = item.name
        holder.binding.tvPrice.text = "₹ ${item.price}"
        holder.binding.tvLocation.text = "${item.city}, ${item.state}"
    }

    override fun getItemCount(): Int = items.size
}
