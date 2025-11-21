package com.example.carkharidlo.ui.listings

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carkharidlo.databinding.ActivityPreviousListingsBinding

class PreviousListings : AppCompatActivity() {

    private lateinit var binding: ActivityPreviousListingsBinding
    private lateinit var adapter: PreviousListingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviousListingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()
        loadListings()
    }

    private fun setupRecycler() {
        adapter = PreviousListingsAdapter()
        binding.recyclerPreviousListings.layoutManager = LinearLayoutManager(this)
        binding.recyclerPreviousListings.adapter = adapter
    }

    private fun loadListings() {
        val prefs = getSharedPreferences("ListingsData", Context.MODE_PRIVATE)

        // Read previously saved listings
        val rawSet = prefs.getStringSet("allListings", emptySet()) ?: emptySet()

        if (rawSet.isEmpty()) {
            // No previous listings → show nothing (empty list)
            adapter.submitList(emptyList())
            return
        }

        val finalList = rawSet.mapNotNull { item ->
            val split = item.split(";;")

            // safety check to avoid crash
            if (split.size < 8) return@mapNotNull null

            ListingModel(
                name = split[0],
                price = split[1],
                contact = split[2],
                email = split[3],
                address = split[4],
                state = split[5],
                city = split[6],
                imageUri = Uri.parse(split[7])
            )
        }

        adapter.submitList(finalList)
    }
}
