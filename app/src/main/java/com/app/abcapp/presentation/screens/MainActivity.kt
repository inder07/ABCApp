package com.app.abcapp.presentation.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.app.abcapp.R
import com.app.abcapp.databinding.ActivityMainBinding
import com.app.abcapp.presentation.adapter.ImagePagerAdapter
import com.app.abcapp.presentation.adapter.ItemAdapter
import com.app.abcapp.presentation.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val images = listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)
        binding.viewPager.adapter = ImagePagerAdapter(images)

        adapter = ItemAdapter(listOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        setupDots(images.size)
        updateDots(viewModel.getCurrentIndex())

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.updatePage(position)
                updateDots(position)
            }
        })

        val searchInput = binding.searchLayout.findViewById<TextInputEditText>(R.id.searchEditText)
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateList(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.filteredList.observe(this) {
            adapter.updateData(it)
        }

        binding.fab.setOnClickListener {
            showStatsBottomSheet()
        }
    }

    private fun setupDots(count: Int) {
        binding.dotsContainer.removeAllViews()
        repeat(count) {
            val dot = ImageView(this).apply {
                setImageResource(R.drawable.dot_unselected)
                val size = resources.getDimensionPixelSize(R.dimen.dot_size)
                layoutParams = LinearLayout.LayoutParams(size, size).apply {
                    val margin = resources.getDimensionPixelSize(R.dimen.dot_margin)
                    setMargins(margin, 0, margin, 0)
                }
            }
            binding.dotsContainer.addView(dot)
        }
    }

    private fun updateDots(index: Int) {
        for (i in 0 until binding.dotsContainer.childCount) {
            val dot = binding.dotsContainer.getChildAt(i) as ImageView
            dot.setImageResource(if (i == index) R.drawable.dot_selected else R.drawable.dot_unselected)
        }
    }

    private fun showStatsBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_stats, null)
        val statText = view.findViewById<TextView>(R.id.stats_text)

        val filtered = viewModel.filteredList.value ?: return
        val currentIndex = viewModel.getCurrentIndex()
        val stat = StringBuilder(getString(R.string.list_stats, currentIndex + 1, filtered.size)).append("\n")

        filtered.joinToString("").groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .take(3)
            .forEach { stat.append("${it.first} = ${it.second}\n") }

        statText.text = stat.toString()
        dialog.setContentView(view)
        dialog.show()
    }
}
