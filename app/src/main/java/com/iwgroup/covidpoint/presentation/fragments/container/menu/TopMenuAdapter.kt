package com.iwgroup.covidpoint.presentation.fragments.container.menu

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.iwgroup.covidpoint.R
import com.iwgroup.covidpoint.databinding.TopMenuItemBinding

class TopMenuAdapter(private val menuIcons: List<Int>) :
    RecyclerView.Adapter<TopMenuAdapter.TopMenuViewHolder>() {

    var onMenuItemClickListener: ((Int) -> Unit)? = null
    private var checkedItemPosition = 0

    inner class TopMenuViewHolder(private val binding: TopMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            fun setMenuItemsBackground(position: Int) {
                if(position == checkedItemPosition)
                    binding.cvImageContainer.setCardBackgroundColor(Color.WHITE)
                else
                    binding.cvImageContainer.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.top_menu_background))
            }

            binding.ivMenuIcon.setImageResource(menuIcons[position])
            setMenuItemsBackground(position)
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
            checkedItemPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = menuIcons.size
}