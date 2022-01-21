package com.iwgroup.covidpoint.presentation.fragments.container.listcountries

import androidx.recyclerview.widget.DiffUtil
import com.iwgroup.covidpoint.data.database.CountryEntity

class ListCountriesDiffUtil(
    private val oldList: List<CountryEntity>,
    private val newList: List<CountryEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: CountryEntity = oldList[oldItemPosition]
        val newItem: CountryEntity = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: CountryEntity = oldList[oldItemPosition]
        val newItem: CountryEntity = newList[newItemPosition]

        return oldItem == newItem
    }
}