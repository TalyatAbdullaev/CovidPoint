package com.example.covidpoint.presentation

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidpoint.R
import com.example.covidpoint.databinding.CustomTabLayoutBinding
import com.example.covidpoint.presentation.fragments.container.menu.TopMenuAdapter

class TabLayoutCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr) {

    private val binding = CustomTabLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private val menuIcons: List<Int> = listOf(R.drawable.ic_map, R.drawable.ic_list)
    private val fragmentsCount: Int = 2
    var onPageClickListener: ((Int) -> Unit)? = null

    init {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.menu.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = TopMenuAdapter(menuIcons)
        binding.menu.adapter = adapter
        adapter.onMenuItemClickListener = {
            if(it < fragmentsCount) { onPageClickListener?.invoke(it) }
        }
    }

//    init {
//        initListeners()
//    }
//
//    private fun initListeners() {
//        binding.ivMenuIcon.setOnClickListener {
//            onPageClickListener?.invoke(PaintCodes.MAP_CODE.value)
//            changeButton(it)
//        }
//        binding.ivListIcon.setOnClickListener {
//            onPageClickListener?.invoke(PaintCodes.LIST_CODE.value)
//            changeButton(it)
//        }
//    }
//
//    private fun changeButton(mainView: View) {
//        fun defaultButtonState() {
//            val defaultColor = ContextCompat.getColor(context, R.color.tab_layout_background)
//            with(binding) {
//                ivMenuIcon.setBackgroundColor(defaultColor)
//                ivListIcon.setBackgroundColor(defaultColor)
//            }
//        }
//
//        defaultButtonState()
//        mainView.setBackgroundColor(Color.WHITE)
//    }
//
//    private enum class PaintCodes (val value: Int) {
//        MAP_CODE(0), LIST_CODE(1)
//    }
}