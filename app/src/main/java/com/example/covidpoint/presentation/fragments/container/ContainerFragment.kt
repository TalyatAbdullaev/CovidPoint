package com.example.covidpoint.presentation.fragments.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covidpoint.R
import com.example.covidpoint.databinding.FragmentContainerBinding
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment


class ContainerFragment : MvpAppCompatFragment() {
    private var _binding: FragmentContainerBinding? = null
    private val binding get() = _binding!!
    private val tabIcons = listOf(R.drawable.ic_map, R.drawable.ic_list)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 2

        binding.viewPager.adapter = FragmentsAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true) { tab, position ->
            tab.setIcon(tabIcons[position])
        }.attach()
    }
}