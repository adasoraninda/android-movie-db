package com.adasoraninda.mymoviedb.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.loadWithLoading
import com.adasoraninda.mymoviedb.databinding.ItemHrMovieBinding
import com.adasoraninda.mymoviedb.databinding.ItemVrMovieBinding
import com.adasoraninda.mymoviedb.domain.model.Movie
import timber.log.Timber

abstract class MovieViewHolder<T : ViewBinding>(open val binding: T) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(movie: Movie)
}

class MovieVrViewHolder(binding: ItemVrMovieBinding) :
    MovieViewHolder<ItemVrMovieBinding>(binding) {
    override fun bind(movie: Movie) {
        Timber.d("$movie")
        val context = binding.root.context

        binding.overviewMovie.text = movie.overview.ifEmpty {
            context.getString(R.string.text_empty)
        }

        binding.titleMovie.text = movie.title.ifEmpty {
            context.getString(R.string.text_empty)
        }

        binding.imageMovie.loadWithLoading(
            movie.posterPath,
            binding.progressBar
        )
    }
}

class MovieHrViewHolder(binding: ItemHrMovieBinding) :
    MovieViewHolder<ItemHrMovieBinding>(binding) {
    override fun bind(movie: Movie) {
        Timber.d("$movie")

        binding.imageMovie.loadWithLoading(
            movie.posterPath,
            binding.progressBar
        )
    }
}