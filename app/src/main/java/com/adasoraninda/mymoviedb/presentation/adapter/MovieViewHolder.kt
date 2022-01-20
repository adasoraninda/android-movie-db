package com.adasoraninda.mymoviedb.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.loadWithLoading
import com.adasoraninda.mymoviedb.databinding.ItemHrMovieBinding
import com.adasoraninda.mymoviedb.databinding.ItemVrMovieBinding
import com.adasoraninda.mymoviedb.domain.model.Movie
import timber.log.Timber

abstract class MovieViewHolder<T : ViewBinding>(
    open val binding: T,
    private val clickListener: ((id: Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(movie: Movie) {
        binding.root.setOnClickListener {
            Timber.d("item clicked")
            clickListener?.invoke(movie.id)
        }
    }
}

class MovieVrViewHolder(
    binding: ItemVrMovieBinding,
    clickListener: ((id: Int) -> Unit)?
) : MovieViewHolder<ItemVrMovieBinding>(binding, clickListener) {
    override fun bind(movie: Movie) {
        super.bind(movie)
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

class MovieHrViewHolder(
    binding: ItemHrMovieBinding,
    clickListener: ((id: Int) -> Unit)?
) : MovieViewHolder<ItemHrMovieBinding>(binding, clickListener) {
    override fun bind(movie: Movie) {
        super.bind(movie)
        Timber.d("$movie")

        binding.imageMovie.loadWithLoading(
            movie.posterPath,
            binding.progressBar
        )
    }
}