package com.example.covidpoint.presentation.fragments.container.menu

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covidpoint.R
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.databinding.TopMenuItemBinding

class TopMenuAdapter(private val menuIcons: List<Int>) :
    RecyclerView.Adapter<TopMenuAdapter.TopMenuViewHolder>() {

    var onMenuItemClickListener: ((Int) -> Unit)? = null

    inner class TopMenuViewHolder(private val binding: TopMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.ivMenuIcon.setImageResource(menuIcons[position])
        }

        fun setMenuItemsBackground() {
            binding.imageContainer.setCardBackgroundColor(Color.WHITE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMenuViewHolder {
        val view = TopMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopMenuViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener {
            onMenuItemClickListener?.invoke(position)
            holder.setMenuItemsBackground()
        }
    }

    override fun getItemCount(): Int = menuIcons.size
}