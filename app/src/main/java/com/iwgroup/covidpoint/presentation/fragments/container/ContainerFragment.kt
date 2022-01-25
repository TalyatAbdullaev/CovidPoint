package com.iwgroup.covidpoint.presentation.fragments.container

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.iwgroup.covidpoint.R
import com.iwgroup.covidpoint.databinding.FragmentContainerBinding
import com.iwgroup.covidpoint.databinding.TopMenuItemBinding
import com.iwgroup.covidpoint.presentation.fragments.container.menu.FragmentsAdapter
import com.iwgroup.covidpoint.presentation.fragments.container.menu.TopMenuAdapter
import moxy.MvpAppCompatFragment


class ContainerFragment : MvpAppCompatFragment() {
    private var _binding: FragmentContainerBinding? = null
    private val binding get() = _binding!!
    private val menuIcons: List<Int> = listOf(R.drawable.ic_map, R.drawable.ic_list)
    lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTopMenu()
    }

    private fun setupTopMenu() {
        viewPager = binding.viewPager

        with(viewPager) {
            isUserInputEnabled = false
            offscreenPageLimit = 2
            adapter = FragmentsAdapter(this@ContainerFragment)
        }

        TabLayoutMediator(binding.topMenu, viewPager) { menuItem, position ->
            menuItem.setIcon(menuIcons[position])
        }.attach()
    }
}