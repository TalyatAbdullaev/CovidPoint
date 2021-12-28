package com.example.covidpoint.presentation.fragments.container.listcountries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.covidpoint.R
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.databinding.CountryItemBinding
import com.example.covidpoint.utils.extentions.drawCountryIntoView

class ListCountriesAdapter :
    RecyclerView.Adapter<ListCountriesAdapter.CountryListViewHolder>() {

    var onItemClickListener: ((CountryEntity) -> Unit)? = null
    val expandedItemsSet = mutableSetOf<Int>()

    var countries: List<CountryEntity> = listOf()
        set(newList) {
            val diffCallback = ListCountriesDiffUtil(field, newList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newList
            diffResult.dispatchUpdatesTo(this)
        }

    inner class CountryListViewHolder(private val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: CountryEntity) {

            binding.drawCountryIntoView(country)

            if(expandedItemsSet.contains(adapterPosition)) {
                binding.childLayout.isExpanded = true
                binding.btnDetailed.text =
                    binding.root.resources.getString(R.string.btn_hide)
            } else {
                binding.childLayout.isExpanded = false
                binding.btnDetailed.text =
                    binding.root.resources.getString(R.string.btn_detail)
            }

            binding.btnDetailed.setOnClickListener {
                val child = binding.childLayout
                if (child.isExpanded) {
                    child.isExpanded = false
                    binding.btnDetailed.text =
                        binding.root.resources.getString(R.string.btn_detail)

                    expandedItemsSet.remove(adapterPosition)
                } else {
                    child.isExpanded = true
                    binding.btnDetailed.text =
                        binding.root.resources.getString(R.string.btn_hide)

                    expandedItemsSet.add(adapterPosition)
                    onItemClickListener?.invoke(country)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size
}