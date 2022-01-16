package com.example.covidpoint.presentation.fragments.container.menu

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.covidpoint.presentation.fragments.container.listcountries.ListCountriesFragment
import com.example.covidpoint.presentation.fragments.container.mapcountries.MapCountriesFragment
import java.lang.IllegalArgumentException

class FragmentsAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MapCountriesFragment()
            1 -> ListCountriesFragment()
            else -> throw IllegalArgumentException()
        }
    }
}