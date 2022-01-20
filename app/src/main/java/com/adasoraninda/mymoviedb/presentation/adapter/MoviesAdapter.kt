package com.adasoraninda.mymoviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.adasoraninda.mymoviedb.databinding.ItemHrMovieBinding
import com.adasoraninda.mymoviedb.databinding.ItemVrMovieBinding
import com.adasoraninda.mymoviedb.domain.model.Movie
import timber.log.Timber

class MoviesAdapter(
    private val type: Type,
    private var clickListener: ((id: Int) -> Unit)? = null
) : ListAdapter<Movie, MovieViewHolder<out ViewBinding>>(DIFF_UTIL) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder<out ViewBinding> {
        val inflater = LayoutInflater.from(parent.context)
        return when (type) {
            Type.HORIZONTAL -> {
                val binding = ItemHrMovieBinding.inflate(inflater, parent, false)
                MovieHrViewHolder(binding, clickListener)
            }
            Type.VERTICAL -> {
                val binding = ItemVrMovieBinding.inflate(inflater, parent, false)
                MovieVrViewHolder(binding, clickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder<out ViewBinding>, position: Int) {
        Timber.d("${getItem(position)}")
        when (type) {
            Type.HORIZONTAL -> (holder as MovieHrViewHolder).bind(getItem(position))
            Type.VERTICAL -> (holder as MovieVrViewHolder).bind(getItem(position))
        }
    }

    enum class Type {
        HORIZONTAL,
        VERTICAL
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

}