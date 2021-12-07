package com.example.covidpoint.presentation.fragments.listcountries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.databinding.FragmentCountiresListBinding

class ListCountriesFragment : Fragment(), ListCountriesInterface {
    private var _binding: FragmentCountiresListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountiresListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ListCountriesAdapter(listOf()).setAboutListener { }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showCountries(countries: List<Country>) {
        binding.recyclerView.adapter = ListCountriesAdapter(countries)
    }
}