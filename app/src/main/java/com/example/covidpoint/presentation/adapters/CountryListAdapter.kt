package com.example.covidpoint.presentation.adapters

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

class CountryListAdapter(private val countries: List<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {
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

                binding.tvConfirmedNum.text = this.latest?.confirmed.toString()
                binding.tvDeathsNum.text = this.latest?.deaths.toString()
                binding.tvRecoveredNum.text = this.latest?.recovered.toString()


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

}