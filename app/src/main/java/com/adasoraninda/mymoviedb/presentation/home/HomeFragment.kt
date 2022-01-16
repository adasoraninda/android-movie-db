package com.adasoraninda.mymoviedb.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.ViewState
import com.adasoraninda.mymoviedb.common.dp
import com.adasoraninda.mymoviedb.databinding.FragmentHomeBinding
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.presentation.adapter.MovieViewHolder
import com.adasoraninda.mymoviedb.presentation.adapter.MoviesAdapter
import com.adasoraninda.mymoviedb.presentation.decorator.ListHorizontalDecorator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val nowPlayingAdapter by lazy {
        MoviesAdapter(MoviesAdapter.Type.HORIZONTAL)
    }

    private val topRatedAdapter by lazy {
        MoviesAdapter(MoviesAdapter.Type.HORIZONTAL)
    }

    private val popularAdapter by lazy {
        MoviesAdapter(MoviesAdapter.Type.HORIZONTAL)
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        binding?.layoutNowPaying?.listMovies?.adapter = null
        binding?.layoutTopRated?.listMovies?.adapter = null
        binding?.layoutPopular?.listMovies?.adapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.layoutNowPaying?.textSeeMore?.visibility = View.INVISIBLE

        binding?.layoutNowPaying?.textLabelMovies?.text =
            getString(R.string.list_hr_label_now_playing)
        binding?.layoutTopRated?.textLabelMovies?.text = getString(R.string.list_hr_label_top_rated)
        binding?.layoutPopular?.textLabelMovies?.text = getString(R.string.list_hr_label_popular)

        setUpList(binding?.layoutNowPaying?.listMovies, nowPlayingAdapter)
        setUpList(binding?.layoutPopular?.listMovies, popularAdapter)
        setUpList(binding?.layoutTopRated?.listMovies, topRatedAdapter)

        binding?.layoutPopular?.textSeeMore?.setOnClickListener {

        }
        binding?.layoutTopRated?.textSeeMore?.setOnClickListener {

        }

        viewModel.nowPlayingState.observe(viewLifecycleOwner) { state ->
            val layout = binding?.layoutNowPaying

            when (state) {
                is ViewState.Error -> {
                    layout?.progressBar?.isVisible = false
                    layout?.textError?.isVisible = true
                    layout?.textError?.text = getString(state.message)
                    layout?.listMovies?.isVisible = false
                }
                ViewState.Loading -> {
                    layout?.progressBar?.isVisible = true
                    layout?.textError?.isVisible = false
                    layout?.listMovies?.isVisible = true
                    nowPlayingAdapter.submitList(emptyList())
                }
                is ViewState.Success -> {
                    Timber.d("now playing: ${state.data}")
                    layout?.progressBar?.isVisible = false
                    layout?.textError?.isVisible = false
                    layout?.listMovies?.isVisible = true
                    nowPlayingAdapter.submitList(state.data)
                }
            }
        }

        viewModel.topRatedState.observe(viewLifecycleOwner) { state ->
            val layout = binding?.layoutTopRated

            when (state) {
                is ViewState.Error -> {
                    layout?.progressBar?.isVisible = false
                    layout?.textError?.isVisible = true
                    layout?.textError?.text = getString(state.message)
                    layout?.listMovies?.isVisible = false
                }
                ViewState.Loading -> {
                    layout?.progressBar?.isVisible = true
                    layout?.textError?.isVisible = false
                    layout?.listMovies?.isVisible = true
                    topRatedAdapter.submitList(emptyList())
                }
                is ViewState.Success -> {
                    Timber.d("top rated: ${state.data}")
                    layout?.progressBar?.isVisible = false
                    layout?.textError?.isVisible = false
                    layout?.listMovies?.isVisible = true
                    topRatedAdapter.submitList(state.data)
                }
            }
        }

        viewModel.popularState.observe(viewLifecycleOwner) { state ->
            val layout = binding?.layoutPopular

            when (state) {
                is ViewState.Error -> {
                    layout?.progressBar?.isVisible = false
                    layout?.textError?.isVisible = true
                    layout?.textError?.text = getString(state.message)
                    layout?.listMovies?.isVisible = false
                }
                ViewState.Loading -> {
                    layout?.progressBar?.isVisible = true
                    layout?.textError?.isVisible = false
                    layout?.listMovies?.isVisible = true
                    popularAdapter.submitList(emptyList())
                }
                is ViewState.Success -> {
                    Timber.d("popular: ${state.data}")
                    layout?.progressBar?.isVisible = false
                    layout?.textError?.isVisible = false
                    layout?.listMovies?.isVisible = true
                    popularAdapter.submitList(state.data)
                }
            }
        }
    }

    private fun setUpList(
        list: RecyclerView?,
        adapter: ListAdapter<Movie, MovieViewHolder<out ViewBinding>>
    ) {
        list?.adapter = adapter
        list?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        list?.addItemDecoration(
            ListHorizontalDecorator(middle = 8.dp)
        )
        list?.setHasFixedSize(true)
    }


}