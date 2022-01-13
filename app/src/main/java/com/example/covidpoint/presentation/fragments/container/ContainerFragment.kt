package com.example.covidpoint.presentation.fragments.container

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidpoint.R
import com.example.covidpoint.databinding.FragmentContainerBinding
import com.example.covidpoint.presentation.fragments.container.menu.TopMenuAdapter
import com.google.android.material.tabs.TabLayout
import moxy.MvpAppCompatFragment


class ContainerFragment : MvpAppCompatFragment() {
    private var _binding: FragmentContainerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupTopMenu(menuIcons: List<Int>) {
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.adapter = FragmentsAdapter(this)

        binding.topMenu.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = TopMenuAdapter(menuIcons)
        binding.topMenu.adapter = adapter
        adapter.onMenuItemClickListener = {

        }
    }
}