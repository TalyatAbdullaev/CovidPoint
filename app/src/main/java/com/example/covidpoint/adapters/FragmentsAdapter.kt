package com.example.covidpoint.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.covidpoint.fragments.ListCountriesFragment
import com.example.covidpoint.fragments.MapCountriesFragment

class FragmentsAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MapCountriesFragment()
            else -> ListCountriesFragment()
        }
    }
}