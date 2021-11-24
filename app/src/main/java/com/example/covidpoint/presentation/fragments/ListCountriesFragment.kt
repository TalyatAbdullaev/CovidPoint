package com.example.covidpoint.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.covidpoint.presentation.Presenter
import com.example.covidpoint.presentation.adapters.CountryListAdapter
import com.example.covidpoint.databinding.FragmentCountiresListBinding
import com.example.covidpoint.data.pojo.Country

class ListCountriesFragment : Fragment() {
    private var _binding: FragmentCountiresListBinding? = null
    private val binding get() = _binding!!
    private val countries = arrayListOf<Country>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountiresListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val presenter = Presenter()
        countries.addAll(presenter.countries)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = CountryListAdapter(countries)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}