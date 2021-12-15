package com.example.covidpoint.presentation.fragments.listcountries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.databinding.FragmentCountiresListBinding
import com.example.covidpoint.di.App
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ListCountriesFragment : MvpAppCompatFragment(), ListCountriesInterface {
    private var _binding: FragmentCountiresListBinding? = null
    private val binding get() = _binding!!
    private var adapter: ListCountriesAdapter? = null

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

        adapter = ListCountriesAdapter()
        binding.recyclerView.adapter = adapter
        adapter?.onItemClickListener = object : ListCountriesAdapter.OnItemClickListener {
            override fun onItemClick(country: CountryEntity) {
                Log.d("TAG", "country -" + country.country)
                val countryId: Int = country.id
                presenter.getCountryStatistic(countryId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showCountries(countries: List<CountryEntity>) {
        adapter?.countries = countries
    }

    override fun showCountryStatistic(country: Country) {
        Log.d("TAG", "country = " + country.timelines?.confirmed?.timeline)


        val dynamicConfirmedKeys: List<String> = country.timelines.let {
            it!!.confirmed.timeline.keys.toList()
        }
        val dynamicConfirmedValues: List<Int> = country.timelines.let {
            it!!.confirmed.timeline.values.toList()
        }

        Log.d("TAG","keys - " + dynamicConfirmedKeys)
    }
}