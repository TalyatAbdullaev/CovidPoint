package com.example.covidpoint.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.covidpoint.R
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.databinding.FragmentBaseBinding
import com.example.covidpoint.presentation.adapters.FragmentsAdapter
import com.example.covidpoint.presentation.presenter.MainView
import com.example.covidpoint.presentation.presenter.Presenter
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class BaseFragment : MvpAppCompatFragment(), MainView {
    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!
    private val tabIcons = listOf(R.drawable.ic_map, R.drawable.ic_list)

    @InjectPresenter
    lateinit var presenter: Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()





        binding.btnNavigate.setOnClickListener {
            findNavController().navigate(R.id.baseFragment)
        }
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = FragmentsAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true) { tab, position ->
            tab.setIcon(tabIcons[position])
        }.attach()
    }

    override fun showCountries(countries: List<Country>) {

    }

    override fun showCountryStatistic(country: Country) {
        TODO("Not yet implemented")
    }
}