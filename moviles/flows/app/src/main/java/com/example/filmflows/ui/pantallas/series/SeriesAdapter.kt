package com.example.filmflows.ui.pantallas.series

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmflows.R
import com.example.filmflows.common.Constantes
import com.example.filmflows.databinding.ItemlistBinding
import com.example.filmflows.domain.modelo.Series

class SeriesAdapter(val actions: SeriesActions) :
    ListAdapter<Series, SeriesAdapter.SeriesViewHolder>(DiffCallback()) {

    interface SeriesActions {
        fun onSeriesWatch(series: Series)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.itemlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class SeriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemlistBinding.bind(view)

        fun bind(
            series: Series,
        ) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(Constantes.imageUrl + series.posterPath)
                    .into(image)

                itemView.setOnClickListener() {
                    actions.onSeriesWatch(series)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Series>() {

        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem == newItem
        }
    }

}