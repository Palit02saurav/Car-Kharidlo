package com.example.carkharidlo.ui.Recommended

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.carkharidlo.databinding.FragmentRecommendedCarzBinding
import com.example.carkharidlo.ui.CarDetails.Hyund_Creta
import com.example.carkharidlo.ui.CarDetails.Kia_Sonnet
import com.example.carkharidlo.ui.CarDetails.MG_Hector
import com.example.carkharidlo.ui.CarDetails.Mahindra_XUV
import com.example.carkharidlo.ui.CarDetails.Skoda_Kushaq
import com.example.carkharidlo.ui.CarDetails.Tata_Harrier
import com.example.carkharidlo.ui.CarDetails.Volkswagon_virtus

class RecommendedFragment : Fragment() {

    private var _binding: FragmentRecommendedCarzBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendedCarzBinding.inflate(inflater, container, false)

        setupNavigation()
        setupSearch()
        setupPremiumInteractions()

        return binding.root
    }

    private fun setupNavigation() {
        binding.cardRecommended1.setOnClickListener {
            animateCard(it)
            startActivity(Intent(requireContext(), Hyund_Creta::class.java))
        }

        binding.cardRecommended2.setOnClickListener {
            animateCard(it)
            startActivity(Intent(requireContext(), Volkswagon_virtus::class.java))
        }

        binding.cardRecommended3.setOnClickListener {
            animateCard(it)
            startActivity(Intent(requireContext(), Mahindra_XUV::class.java))
        }

        binding.cardRecommended4.setOnClickListener {
            animateCard(it)
            startActivity(Intent(requireContext(), Tata_Harrier::class.java))
        }

        binding.cardRecommended5.setOnClickListener {
            animateCard(it)
            startActivity(Intent(requireContext(), Kia_Sonnet::class.java))
        }

        binding.cardRecommended6.setOnClickListener {
            animateCard(it)
            startActivity(Intent(requireContext(), MG_Hector::class.java))
        }

        binding.cardRecommended7.setOnClickListener {
            animateCard(it)
            startActivity(Intent(requireContext(), Skoda_Kushaq::class.java))
        }
    }

    private fun setupSearch() {
        binding.searchRecommended.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()

                if (query.isEmpty()) {
                    showAllCards()
                    return
                }

                binding.cardRecommended1.visibility =
                    if ("hyundai creta".contains(query) || "creta".contains(query) || "hyundai".contains(query)) View.VISIBLE else View.GONE

                binding.cardRecommended2.visibility =
                    if ("volkswagen virtus".contains(query) || "virtus".contains(query) || "volkswagen".contains(query) || "volkswagon".contains(query)) View.VISIBLE else View.GONE

                binding.cardRecommended3.visibility =
                    if ("mahindra xuv".contains(query) || "xuv".contains(query) || "mahindra".contains(query)) View.VISIBLE else View.GONE

                binding.cardRecommended4.visibility =
                    if ("tata harrier".contains(query) || "harrier".contains(query) || "tata".contains(query)) View.VISIBLE else View.GONE

                binding.cardRecommended5.visibility =
                    if ("kia sonnet".contains(query) || "sonnet".contains(query) || "kia".contains(query)) View.VISIBLE else View.GONE

                binding.cardRecommended6.visibility =
                    if ("mg hector".contains(query) || "hector".contains(query) || "mg".contains(query)) View.VISIBLE else View.GONE

                binding.cardRecommended7.visibility =
                    if ("skoda kushaq".contains(query) || "kushaq".contains(query) || "skoda".contains(query)) View.VISIBLE else View.GONE
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })
    }

    private fun showAllCards() {
        binding.cardRecommended1.visibility = View.VISIBLE
        binding.cardRecommended2.visibility = View.VISIBLE
        binding.cardRecommended3.visibility = View.VISIBLE
        binding.cardRecommended4.visibility = View.VISIBLE
        binding.cardRecommended5.visibility = View.VISIBLE
        binding.cardRecommended6.visibility = View.VISIBLE
        binding.cardRecommended7.visibility = View.VISIBLE
    }

    private fun setupPremiumInteractions() {
        binding.subtitleRecommended.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Top premium recommendations curated for you",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun animateCard(view: View) {
        view.animate()
            .scaleX(0.96f)
            .scaleY(0.96f)
            .setDuration(100)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(120)
                    .start()
            }
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}