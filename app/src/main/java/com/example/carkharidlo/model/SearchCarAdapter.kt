package com.example.carkharidlo.ui.Home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carkharidlo.databinding.ItemCarCardBinding
import com.example.carkharidlo.model.SearchCar

class SearchCarAdapter(
    private val carList: List<SearchCar>
) : RecyclerView.Adapter<SearchCarAdapter.CarViewHolder>() {

    inner class CarViewHolder(val binding: ItemCarCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]

        holder.binding.carImage.setImageResource(car.imageRes)
        holder.binding.carName.text = car.name
        holder.binding.carPrice.text = car.price
        holder.binding.carDetails.text = car.details

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, car.targetActivity)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = carList.size
}