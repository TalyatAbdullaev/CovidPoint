package com.example.covidpoint.presentation.fragments.listcountries

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.with
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.covidpoint.R
import com.example.covidpoint.data.network.utils.Urls
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.databinding.CountryItemBinding
import java.util.*

class ListCountriesAdapter(private val countries: List<Country>) :
    RecyclerView.Adapter<ListCountriesAdapter.CountryListViewHolder>() {
    private var aboutListener: (Int) -> Unit = {}

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

                binding.tvConfirmedNum.text = this.latest.confirmed.toString()
                binding.tvDeathsNum.text = this.latest.deaths.toString()
                binding.tvRecoveredNum.text = this.latest.recovered.toString()

                val confirmed = this.latest.confirmed
                val deaths = this.latest.deaths
                val recovered = this.latest.recovered

                val sum = confirmed + deaths + recovered

                binding.pbConfirmed.progress = setProgress(confirmed, sum)
                binding.pbDeaths.progress = setProgress(deaths, sum)
                binding.pbRecovered.progress = setProgress(recovered, sum)

                val flagUrl: String =
                    String.format(
                        Urls.COUNTRY_FLAG_URL,
                        this.countryCode.toString().lowercase(Locale.getDefault())
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
                        aboutListener.invoke(adapterPosition)
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

    private fun setProgress(value: Int, sum: Int): Int = (value/sum)*100
}