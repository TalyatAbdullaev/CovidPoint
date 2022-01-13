package com.example.covidpoint.presentation

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.covidpoint.R
import com.example.covidpoint.databinding.CustomTabLayoutBinding

class TabLayoutCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val binding = CustomTabLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    var onPageClickListener: ((Int) -> Unit)? = null

    init {
        initListeners()
    }

    private fun initListeners() {
        binding.ivMapIcon.setOnClickListener {
            onPageClickListener?.invoke(PaintCodes.MAP_CODE.value)
            changeButton(it)
        }
        binding.ivListIcon.setOnClickListener {
            onPageClickListener?.invoke(PaintCodes.LIST_CODE.value)
            changeButton(it)
        }
    }

    private fun changeButton(mainView: View) {
        fun defaultButtonState() {
            val defaultColor = ContextCompat.getColor(context, R.color.tab_layout_background)
            with(binding) {
                ivMapIcon.setBackgroundColor(defaultColor)
                ivListIcon.setBackgroundColor(defaultColor)
            }
        }

        defaultButtonState()
        mainView.setBackgroundColor(Color.WHITE)
    }

    private enum class PaintCodes (val value: Int) {
        MAP_CODE(0), LIST_CODE(1)
    }
}