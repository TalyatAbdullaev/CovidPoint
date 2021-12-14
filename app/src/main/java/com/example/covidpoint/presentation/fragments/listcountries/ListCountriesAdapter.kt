package com.example.covidpoint.presentation.fragments.listcountries

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.with
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.covidpoint.R
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.network.utils.Urls
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.databinding.CountryItemBinding
import java.util.*

class ListCountriesAdapter :
    RecyclerView.Adapter<ListCountriesAdapter.CountryListViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    var countries: List<CountryEntity> = listOf()
        set(newList) {
            val diffCallback = ListCountriesDiffUtil(field, newList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newList
            diffResult.dispatchUpdatesTo(this)
        }

    inner class CountryListViewHolder(val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        with(holder) {
            with(countries[position]) {
                binding.tvCountryName.text = this.country

                val confirmed = this.confirmed
                val deaths = this.deaths
                val recovered = this.recovered

                binding.tvConfirmedNum.text = confirmed.toString()
                binding.tvDeathsNum.text = deaths.toString()
                binding.tvRecoveredNum.text = recovered.toString()

                val sum = confirmed + deaths + recovered

                binding.pbConfirmed.progress = progressValue(confirmed, sum)
                binding.pbDeaths.progress = progressValue(deaths, sum)
                binding.pbRecovered.progress = progressValue(recovered, sum)

                val flagUrl: String =
                    String.format(
                        Urls.COUNTRY_FLAG_URL,
                        this.countryCode.lowercase(Locale.getDefault())
                    )
                Log.d("TAG", "flagUrl = " + flagUrl)

                setFlagIntoIV(binding.ivFlag, flagUrl)

                binding.btnDetailed.setOnClickListener {
                    val child = binding.childLayout
                    if (child.isExpanded) {
                        child.isExpanded = false
                        binding.btnDetailed.text =
                            holder.itemView.resources.getString(R.string.btn_detail)
                    } else {
                        child.isExpanded = true
                        binding.btnDetailed.text =
                            holder.itemView.resources.getString(R.string.btn_hide)

                        onItemClickListener?.onItemClick(countries[position])
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = countries.size

    private fun setFlagIntoIV(imageView: ImageView, photoUrl: String) {
        with(imageView.context)
            .asBitmap()
            .load(photoUrl)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    private fun progressValue(value: Int, sum: Int): Int = (value / sum) * 100

    interface OnItemClickListener {
        fun onItemClick(country: CountryEntity)
    }
}