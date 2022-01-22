package com.adasoraninda.mymoviedb.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.*
import com.adasoraninda.mymoviedb.databinding.FragmentDetailMovieBinding
import com.adasoraninda.mymoviedb.domain.model.MovieDetail
import com.adasoraninda.mymoviedb.presentation.adapter.MoviesAdapter
import com.adasoraninda.mymoviedb.presentation.decorator.ListHorizontalDecorator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.SAVE_PEEK_HEIGHT
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
        MoviesAdapter(MoviesAdapter.Type.HORIZONTAL, this::navigateToDetail)
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
        setUpBottomSheet()
        buttonClickListener()

        viewModel.message.observe(viewLifecycleOwner) { message ->
            binding?.root?.let {
                Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
            }
        }

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
                    layout?.progressBarRecommendations?.isVisible = false
                    layout?.textLabelRecommendations?.isVisible = true
                    layout?.textErrorRecommendations?.isVisible = true
                    layout?.textErrorRecommendations?.text = getString(state.message)
                    listAdapter.submitList(emptyList())
                }
                ViewState.Loading -> {
                    layout?.progressBarRecommendations?.isVisible = true
                    layout?.textLabelRecommendations?.isVisible = false
                    layout?.textErrorRecommendations?.isVisible = false
                    listAdapter.submitList(emptyList())
                }
                is ViewState.Success -> {
                    Timber.d("recommendations: ${state.data}")
                    layout?.progressBarRecommendations?.isVisible = false
                    layout?.textLabelRecommendations?.isVisible = true
                    layout?.textErrorRecommendations?.isVisible = false
                    listAdapter.submitList(state.data)
                }
            }
        }

        viewModel.status.observe(viewLifecycleOwner) { isFavorite ->
            changeButtonDrawable(isFavorite)
        }

    }

    private fun setUpBottomSheet() {
        val view = binding?.movieBottomSheet ?: return

        val screenHeight = resources.displayMetrics.heightPixels
        val percent = 24
        val getPercentageFromHeight = (screenHeight * percent / 100)
        val buttonBackHeight = binding?.buttonImageBack?.drawable?.intrinsicHeight ?: 0

        val bottomSheetBehavior = BottomSheetBehavior.from(view)

        bottomSheetBehavior.isDraggable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        bottomSheetBehavior.peekHeight = getPercentageFromHeight
        bottomSheetBehavior.saveFlags = SAVE_PEEK_HEIGHT
        bottomSheetBehavior.maxHeight = screenHeight - buttonBackHeight.dp

        Timber.d("display height size: $screenHeight")
        Timber.d("$percent% from height size: $getPercentageFromHeight")
        Timber.d("buttonBackHeight: $buttonBackHeight")

    }

    private fun setUpList() {
        Timber.d("setup list")
        val list = binding?.layoutBottomSheet?.listMovies

        list?.adapter = listAdapter
        list?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        list?.addItemDecoration(
            ListHorizontalDecorator(middle = 8.dp)
        )
        list?.setHasFixedSize(true)
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

        button?.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }

    private fun buttonClickListener() {
        binding?.buttonImageBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding?.layoutBottomSheet?.buttonWatchlist?.setOnClickListener {
            viewModel.toggleWatchlistButton()
        }
    }

    private fun navigateToDetail(id: Int) {
        val action = DetailFragmentDirections.navToDetail(id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.layoutBottomSheet?.listMovies?.adapter = null
        _binding = null
    }

}