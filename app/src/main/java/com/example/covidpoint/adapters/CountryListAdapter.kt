package com.example.covidpoint.adapters

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.covidpoint.api.Urls
import com.example.covidpoint.databinding.CountryItemBinding
import com.example.covidpoint.pojo.Country

class CountryListAdapter(private val countries: List<Country>): RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {
    inner class CountryListViewHolder(val binding: CountryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        with(holder){
            with(countries[position]){
                val countryName = binding.tvCountryName
                val confirmed = binding.tvConfirmedNum
                val imageView = binding.ivFlag
                val flagUrl: String = String.format(Urls.COUNTRY_FLAG_URL, this.countryCode)
                Log.d("TAG", "flagUrl = " + flagUrl)

                countryName.text = this.country
                confirmed.text = this.latest?.confirmed.toString()
                setFlagIntoIV(holder.itemView.context, imageView, flagUrl)
            }
        }
    }

    override fun getItemCount(): Int = countries.size

    private fun setFlagIntoIV(context: Context, imageView: ImageView, photoUrl: String) {
        Glide.with(context)
            .asBitmap()
            .load(photoUrl)
            .circleCrop()
            .addListener(object: RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    TODO("Not yet implemented")
                }


            })
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

}