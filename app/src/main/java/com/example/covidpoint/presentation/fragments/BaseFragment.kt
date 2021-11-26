package com.example.covidpoint.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covidpoint.R
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.databinding.FragmentBaseBinding
import com.example.covidpoint.di.App
import com.example.covidpoint.presentation.adapters.FragmentsAdapter
import com.example.covidpoint.presentation.presenter.MainView
import com.example.covidpoint.presentation.presenter.Presenter
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class BaseFragment : MvpAppCompatFragment(), MainView {
    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!
    private val tabIcons = listOf(R.drawable.ic_map, R.drawable.ic_list)

    @Inject
    lateinit var presenterProvider: Provider<Presenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.graph.inject(this)
        super.onCreate(savedInstanceState)
    }

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
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = FragmentsAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true) { tab, position ->
            tab.setIcon(tabIcons[position])
        }.attach()
    }

    override fun getCountries(countries: List<Country>) {
        Log.d("TAG", "отработало - " + childFragmentManager.fragments.size)

        val listCountriesFragment =
            childFragmentManager.findFragmentById(R.id.listCountriesFragment) as ListCountriesFragment

        //listCountriesFragment.showListCountries(countries)
    }

    override fun getCountryStatistic(country: Country) {
        TODO("Not yet implemented")
    }
}