package com.example.covidpoint.presentation.fragments.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidpoint.R
import com.example.covidpoint.databinding.FragmentContainerBinding
import com.example.covidpoint.presentation.fragments.container.menu.FragmentsAdapter
import com.example.covidpoint.presentation.fragments.container.menu.TopMenuAdapter
import moxy.MvpAppCompatFragment


class ContainerFragment : MvpAppCompatFragment() {
    private var _binding: FragmentContainerBinding? = null
    private val binding get() = _binding!!
    private val menuIcons: List<Int> = listOf(R.drawable.ic_map, R.drawable.ic_list)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTopMenu(menuIcons, 2)
    }

    private fun setupTopMenu(menuIcons: List<Int>, fragmentsCount: Int) {
        val viewPager = binding.viewPager

        with(viewPager) {
            isUserInputEnabled = false
            offscreenPageLimit = menuIcons.size
            adapter = FragmentsAdapter(this@ContainerFragment)
        }

        binding.topMenu.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = TopMenuAdapter(menuIcons)
        binding.topMenu.adapter = adapter
        adapter.onMenuItemClickListener = {
            if(it < fragmentsCount) { viewPager.currentItem = it }
        }
    }
}