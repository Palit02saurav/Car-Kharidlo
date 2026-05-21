package com.example.carkharidlo.ui.Latest_Carz

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carkharidlo.databinding.ItemCarBinding
import com.example.carkharidlo.model.Car
import com.example.carkharidlo.ui.CarDetails.UserCarDetailsActivity
import java.io.File

class LatestCarAdapter(
    private val carList: List<Car>
) : RecyclerView.Adapter<LatestCarAdapter.CarViewHolder>() {

    inner class CarViewHolder(val binding: ItemCarBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]

        holder.binding.carName.text = car.name
        holder.binding.carPrice.text = "₹ ${car.price}"
        holder.binding.carDriven.text = "Driven: ${car.drivenKm} km"
        holder.binding.carSpecs.text = "${car.fuelType} | ${car.transmission}"

        val file = File(car.imagePath)
        if (file.exists()) {
            holder.binding.carImage.setImageURI(Uri.fromFile(file))
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, UserCarDetailsActivity::class.java)

            intent.putExtra("carId", car.id)
            intent.putExtra("carName", car.name)
            intent.putExtra("carPrice", car.price)
            intent.putExtra("drivenKm", car.drivenKm)
            intent.putExtra("fuelType", car.fuelType)
            intent.putExtra("transmission", car.transmission)
            intent.putExtra("imagePath", car.imagePath)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = carList.size
}