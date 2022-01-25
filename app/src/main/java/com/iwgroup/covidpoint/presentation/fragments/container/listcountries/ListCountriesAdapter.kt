package com.iwgroup.covidpoint.presentation.fragments.container.listcountries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.iwgroup.covidpoint.R
import com.iwgroup.covidpoint.data.database.countries.CountryEntity
import com.iwgroup.covidpoint.databinding.CountriesListItemBinding
import com.iwgroup.covidpoint.databinding.CountryItemBinding
import com.iwgroup.covidpoint.utils.extentions.drawCountryIntoView

class ListCountriesAdapter :
    RecyclerView.Adapter<ListCountriesAdapter.CountryListViewHolder>() {

    var onItemClickListener: ((CountryEntity) -> Unit)? = null
    private val expandedItemsSet = mutableSetOf<Int>()

    var countries: List<CountryEntity> = listOf()
        set(newList) {
            val diffCallback = ListCountriesDiffUtil(field, newList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newList
            diffResult.dispatchUpdatesTo(this)
        }

    inner class CountryListViewHolder(private val binding: CountriesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: CountryEntity) {
            binding.countryItem.drawCountryIntoView(country)
            checkExpanded(binding.countryItem, adapterPosition)
            setItemClickListener(binding.countryItem, adapterPosition, country)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val view =
            CountriesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size

    private fun checkExpanded(item: CountryItemBinding, adapterPosition: Int) {
        with(item) {
            if (expandedItemsSet.contains(countries[adapterPosition].id)) {
                childLayout.isExpanded = true
                btnDetailed.text =
                    root.resources.getString(R.string.btn_hide)
            } else {
                childLayout.isExpanded = false
                btnDetailed.text =
                    root.resources.getString(R.string.btn_detailed)
            }
        }
    }

    private fun setItemClickListener(
        item: CountryItemBinding,
        adapterPosition: Int,
        country: CountryEntity
    ) {
        with(item) {
            btnDetailed.setOnClickListener {
                val child = childLayout
                if (child.isExpanded) {
                    child.isExpanded = false
                    btnDetailed.text =
                        root.resources.getString(R.string.btn_detailed)

                    expandedItemsSet.remove(countries[adapterPosition].id)
                } else {
                    child.isExpanded = true
                    btnDetailed.text =
                        root.resources.getString(R.string.btn_hide)

                    expandedItemsSet.add(countries[adapterPosition].id)
                    onItemClickListener?.invoke(country)
                }
            }
        }
    }
}