package com.adasoraninda.mymoviedb.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adasoraninda.mymoviedb.R
import com.adasoraninda.mymoviedb.common.Constant
import com.adasoraninda.mymoviedb.common.ViewState
import com.adasoraninda.mymoviedb.common.dp
import com.adasoraninda.mymoviedb.databinding.FragmentSearchBinding
import com.adasoraninda.mymoviedb.presentation.adapter.MoviesAdapter
import com.adasoraninda.mymoviedb.presentation.decorator.ListVerticalDecorator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding

    private val listAdapter by lazy {
        MoviesAdapter(MoviesAdapter.Type.VERTICAL, this::navigateToDetail)
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpList()

        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    viewModel.searchData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Error -> {
                    binding?.progressBar?.isVisible = false
                    binding?.textError?.isVisible = true
                    binding?.textError?.text = getString(state.message)
                    binding?.listMovies?.isVisible = false
                }
                ViewState.Loading -> {
                    binding?.progressBar?.isVisible = true
                    binding?.textError?.isVisible = false
                    binding?.listMovies?.isVisible = true
                    listAdapter.submitList(emptyList())
                }
                is ViewState.Success -> {
                    Timber.d("search: ${state.data}")
                    binding?.progressBar?.isVisible = false
                    binding?.textError?.isVisible = false
                    binding?.listMovies?.isVisible = true
                    listAdapter.submitList(state.data)
                }
            }
        }

    }

    private fun navigateToDetail(id: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri("${Constant.pathDestination}/detail/$id".toUri())
            .build()

        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.anim_in_left)
            .setExitAnim(R.anim.anim_out_left)
            .setPopEnterAnim(R.anim.anim_in_right)
            .setPopExitAnim(R.anim.anim_out_right)
            .build()

        findNavController().navigate(request,navOptions)
    }

    private fun setUpList() {
        binding?.listMovies?.adapter = listAdapter
        binding?.listMovies?.layoutManager = LinearLayoutManager(requireContext())
        binding?.listMovies?.addItemDecoration(
            ListVerticalDecorator(
                top = 8.dp,
                bottom = 8.dp,
                leftAndRight = 16.dp
            )
        )
        binding?.listMovies?.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.listMovies?.adapter = null
        _binding = null
    }

}