package com.adasoraninda.mymoviedb.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.*
import com.adasoraninda.mymoviedb.databinding.FragmentDetailMovieBinding
import com.adasoraninda.mymoviedb.domain.model.MovieDetail
import com.adasoraninda.mymoviedb.presentation.adapter.MoviesAdapter
import com.adasoraninda.mymoviedb.presentation.decorator.ListHorizontalDecorator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels()

    private val listAdapter by lazy {
        MoviesAdapter(MoviesAdapter.Type.HORIZONTAL)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        if (savedInstanceState == null) {
            viewModel.fetchMovieDetail(args.movieId)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpList()
        buttonListener()

        viewModel.movie.observe(viewLifecycleOwner) { state ->
            val layout = binding?.layoutBottomSheet

            when (state) {
                is ViewState.Error -> {
                    layout?.root?.isVisible = false
                    binding?.progressBar?.isVisible = false
                    binding?.textError?.isVisible = true
                    binding?.textError?.text = getString(state.message)
                }
                ViewState.Loading -> {
                    layout?.root?.isVisible = false
                    binding?.progressBar?.isVisible = true
                    binding?.textError?.isVisible = false
                }
                is ViewState.Success -> {
                    layout?.root?.isVisible = true
                    binding?.progressBar?.isVisible = false
                    binding?.textError?.isVisible = false
                    setData(state.data)
                }
            }
        }

        viewModel.movieRecommendations.observe(viewLifecycleOwner) { state ->
            val layout = binding?.layoutBottomSheet

            when (state) {
                is ViewState.Error -> {
                    layout?.textLabelRecommendations?.isVisible = true
                    layout?.textErrorRecommendations?.isVisible = true
                    layout?.listMovies?.isVisible = false
                    layout?.textErrorRecommendations?.text = getString(state.message)
                }
                ViewState.Loading -> {
                    layout?.textLabelRecommendations?.isVisible = false
                    layout?.textErrorRecommendations?.isVisible = false
                    layout?.listMovies?.isVisible = false
                    listAdapter.submitList(emptyList())
                }
                is ViewState.Success -> {
                    Timber.d("${state.data}")
                    layout?.textLabelRecommendations?.isVisible = true
                    layout?.textErrorRecommendations?.isVisible = false
                    layout?.listMovies?.isVisible = true
                    listAdapter.submitList(state.data)
                }
            }
        }

        viewModel.status.observe(viewLifecycleOwner) { isFavorite ->
            changeButtonDrawable(isFavorite)
        }

    }

    private fun setUpList() {
        val layout = binding?.layoutBottomSheet

        layout?.listMovies?.adapter = listAdapter

        layout?.listMovies?.setHasFixedSize(true)

        layout?.listMovies?.addItemDecoration(
            ListHorizontalDecorator(middle = 8.dp)
        )

        layout?.listMovies?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

    }

    private fun buttonListener() {
        val layout = binding?.layoutBottomSheet

        layout?.buttonWatchlist?.setOnClickListener {
            viewModel.toggleWatchlistButton()
        }
    }

    private fun setData(data: MovieDetail) {
        val layout = binding?.layoutBottomSheet

        layout?.textRunTime?.text = data.runtime.toHourMinute()
        layout?.textRateMovie?.text = data.voteAverage.toString()
        layout?.ratingMovie?.rating = data.voteAverage.toRate5()

        layout?.textTitle?.text = data.title.ifEmpty {
            getString(R.string.text_empty)
        }
        layout?.textGenres?.text = data.genres.ifEmpty {
            getString(R.string.text_empty)
        }
        layout?.textOverview?.text = data.overview.ifEmpty {
            getString(R.string.text_empty)
        }

        binding?.imageMovie?.load("${Constant.imageFormat}${data.posterPath}") {
            crossfade(true)
            placeholder(R.color.teal_200)
        }
    }

    private fun changeButtonDrawable(isFavorite: Boolean) {
        val button = binding?.layoutBottomSheet?.buttonWatchlist

        val drawable = if (isFavorite) {
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_check)
        } else {
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_add)
        }

        button?.setCompoundDrawables(drawable, null, null, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.layoutBottomSheet?.listMovies?.adapter = null
        _binding = null
    }

}