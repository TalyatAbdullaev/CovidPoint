package com.example.covidpoint

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.covidpoint.adapters.FragmentsAdapter
import com.example.covidpoint.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: Presenter
    private val tabIcons = listOf(R.drawable.ic_map, R.drawable.ic_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        viewPager.adapter = FragmentsAdapter(this)
        TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            tab.setIcon(tabIcons[position])
        }.attach()

        presenter = Presenter()
        presenter.getCountries()

    }


}