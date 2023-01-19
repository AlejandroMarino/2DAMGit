package com.example.filmflows.ui.pantallas.content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.filmflows.R
import com.example.filmflows.databinding.ItemBinding
import com.example.filmflows.domain.modelo.Movie

class MoviesAdapter(val actions: MovieActions) :
    ListAdapter<Movie, MoviesAdapter.MoviesViewHolder>(DiffCallback()) {

    interface MovieActions {
        fun onMovieWatch(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemBinding.bind(view)

        fun bind(
            movie: Movie,
        ) {
            with(binding) {
                name.text = movie.title

                itemView.setOnClickListener {
                    actions.onMovieWatch(movie)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }


}