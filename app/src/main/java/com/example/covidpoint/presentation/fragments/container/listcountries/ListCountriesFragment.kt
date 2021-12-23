package com.example.covidpoint.presentation.fragments.container.listcountries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.databinding.FragmentCountiresListBinding
import com.example.covidpoint.di.App
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ListCountriesFragment : MvpAppCompatFragment(), ListCountriesInterface {
    private var _binding: FragmentCountiresListBinding? = null
    private val binding get() = _binding!!
    private val adapter: ListCountriesAdapter by lazy { ListCountriesAdapter() }

    @Inject
    lateinit var presenterProvider: Provider<ListCountriesPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.graph.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountiresListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
        adapter.onItemClickListener = {
            Log.d("TAG", "country -" + it.country)
            val countryId: Int = it.id
            presenter.getCountryStatistic(countryId)
        }

        val itemAnimator = binding.recyclerView.itemAnimator
        if(itemAnimator is DefaultItemAnimator)
            itemAnimator.supportsChangeAnimations = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showCountries(countries: List<CountryEntity>) {
        adapter.countries = countries
    }

    override fun showCountryStatistic(country: CountryEntity) {
        Log.d("TAG", "country - " + country.country)

        val countries = arrayListOf<CountryEntity>()
        countries.addAll(adapter.countries)

        countries.forEachIndexed let@ { index, countryEntity ->
            if (countryEntity.id == country.id) {
                countries[index] = country
                return@let
            }
        }
        adapter.countries = countries
    }
}