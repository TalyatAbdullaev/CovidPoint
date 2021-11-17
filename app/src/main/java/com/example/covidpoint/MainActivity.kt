package com.example.covidpoint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.ListFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.covidpoint.api.ApiFactory
import com.example.covidpoint.databinding.ActivityMainBinding
import com.example.covidpoint.fragments.MapFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        viewPager.adapter = FragmentAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabNames = listOf("MAP", "LIST")
            tab.text = tabNames[position]
        }.attach()

        val presenter = Presenter()
        presenter.getCountries()

    }

    class FragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
          return 2
        }

        override fun createFragment(position: Int): Fragment {
           return when(position) {
               0 -> MapFragment()
               1 -> ListFragment()
               else -> ListFragment()
           }
        }
    }
}