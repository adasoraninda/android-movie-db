package com.adasoraninda.mymoviedb.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.Constant
import com.adasoraninda.mymoviedb.common.ViewState
import com.adasoraninda.mymoviedb.common.dp
import com.adasoraninda.mymoviedb.databinding.FragmentHomeBinding
import com.adasoraninda.mymoviedb.databinding.LayoutListHorizontalBinding
import com.adasoraninda.mymoviedb.domain.model.Movie
import com.adasoraninda.mymoviedb.presentation.adapter.MoviesAdapter
import com.adasoraninda.mymoviedb.presentation.decorator.ListHorizontalDecorator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val nowPlayingAdapter by lazy {
        MoviesAdapter(MoviesAdapter.Type.HORIZONTAL, this::navigateToDetail)
    }

    private val topRatedAdapter by lazy {
        MoviesAdapter(MoviesAdapter.Type.HORIZONTAL, this::navigateToDetail)
    }

    private val popularAdapter by lazy {
        MoviesAdapter(MoviesAdapter.Type.HORIZONTAL, this::navigateToDetail)
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("home life cycle create")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("home life cycle destroy")
        binding?.layoutNowPaying?.listMovies?.adapter = null
        binding?.layoutTopRated?.listMovies?.adapter = null
        binding?.layoutPopular?.listMovies?.adapter = null
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("home life cycle created")
        binding?.layoutNowPaying?.textSeeMore?.visibility = View.INVISIBLE

        binding?.layoutNowPaying?.textLabelMovies?.text =
            getString(R.string.home_list_hr_label_now_playing)
        binding?.layoutTopRated?.textLabelMovies?.text =
            getString(R.string.home_list_hr_label_top_rated)
        binding?.layoutPopular?.textLabelMovies?.text =
            getString(R.string.home_list_hr_label_popular)

        setUpList(binding?.layoutNowPaying?.listMovies, nowPlayingAdapter)
        setUpList(binding?.layoutPopular?.listMovies, popularAdapter)
        setUpList(binding?.layoutTopRated?.listMovies, topRatedAdapter)

        binding?.layoutPopular?.textSeeMore?.setOnClickListener {
            navToDeepLinking("/popular")
        }

        binding?.layoutTopRated?.textSeeMore?.setOnClickListener {
            navToDeepLinking("/top")
        }

        viewModel.nowPlayingState.observe(viewLifecycleOwner) { state ->
            val layout = binding?.layoutNowPaying
            handleState(state, layout, nowPlayingAdapter)
        }

        viewModel.topRatedState.observe(viewLifecycleOwner) { state ->
            val layout = binding?.layoutTopRated
            handleState(state, layout, topRatedAdapter)
        }

        viewModel.popularState.observe(viewLifecycleOwner) { state ->
            val layout = binding?.layoutPopular
            handleState(state, layout, popularAdapter)
        }
    }

    private fun navigateToDetail(id: Int) {
        navToDeepLinking("/detail/$id")
    }

    private fun navToDeepLinking(destination: String) {
        val request = NavDeepLinkRequest.Builder
            .fromUri("${Constant.pathDestination}$destination".toUri())
            .build()

        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.anim_in_left)
            .setExitAnim(R.anim.anim_out_left)
            .setPopEnterAnim(R.anim.anim_in_right)
            .setPopExitAnim(R.anim.anim_out_right)
            .build()

        findNavController().navigate(request, navOptions)
    }

    private fun handleState(
        state: ViewState<List<Movie>>,
        layout: LayoutListHorizontalBinding?,
        adapter: MoviesAdapter
    ) {
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
                adapter.submitList(emptyList())
            }
            is ViewState.Success -> {
                Timber.d("data: ${state.data}")
                layout?.progressBar?.isVisible = false
                layout?.textError?.isVisible = false
                layout?.listMovies?.isVisible = true
                adapter.submitList(state.data)
            }
        }
    }

    private fun setUpList(
        list: RecyclerView?,
        adapter: MoviesAdapter
    ) {
        Timber.d("setup list")
        list?.adapter = adapter
        list?.addItemDecoration(
            ListHorizontalDecorator(middle = 8.dp)
        )
        list?.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
    }

}