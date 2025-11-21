package com.example.carkharidlo.ui.Home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carkharidlo.R
import com.example.carkharidlo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())
    private var currentIndex = 0
    private val imageCount = 4

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        val scrollView = binding.horizontalScrollView
        startAutoScroll(scrollView)

        return root
    }


    private fun startAutoScroll(scrollView: HorizontalScrollView) {
        val runnable = object : Runnable {
            override fun run() {
                val imageWidthDp = 350 + 20
                val imageWidthPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    imageWidthDp.toFloat(),
                    resources.displayMetrics
                ).toInt()

                currentIndex = (currentIndex + 1) % imageCount
                val scrollToX = currentIndex * imageWidthPx
                scrollView.smoothScrollTo(scrollToX, 0)
                handler.postDelayed(this, 3000)
            }
        }

        handler.postDelayed(runnable, 3000)

        scrollView.setOnTouchListener { _, _ ->
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed({ startAutoScroll(scrollView) }, 5000)
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }
}
