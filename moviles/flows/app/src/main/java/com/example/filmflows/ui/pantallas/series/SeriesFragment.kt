package com.example.filmflows.ui.pantallas.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmflows.R
import com.example.filmflows.databinding.FragmentMoviesBinding
import com.example.filmflows.databinding.FragmentSeriesBinding
import com.example.filmflows.domain.modelo.Series
import com.example.filmflows.ui.pantallas.MainActivity
import com.example.filmflows.ui.pantallas.movies.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeriesFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding
    private lateinit var adapter: SeriesAdapter

    private val viewModel: SeriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.popularSeries)
        viewModel.handleEvent(SeriesEvent.GetPopularSeries)

        init()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    if (value.error != null) {
                        Toast.makeText(
                            requireContext(),
                            viewModel.uiState.value.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    binding.loading.visibility =
                        if (value.isLoading) View.VISIBLE else View.GONE
                    if (value.series != null) {
                        adapter.submitList(value.series)
                    }
                }
            }
        }

    }

    private fun init() {
        val layoutManager = LinearLayoutManager(this.context)
        val dividerItemDecoration = DividerItemDecoration(
            binding.viewPager.context,
            layoutManager.orientation
        )
        binding.viewPager.addItemDecoration(dividerItemDecoration)

        adapter = SeriesAdapter(object : SeriesAdapter.SeriesActions {
            override fun onSeriesWatch(series: Series) {
                val action = SeriesFragmentDirections.actionSeriesFragmentToSeriesDetailFragment(series.id)
                findNavController().navigate(action)
            }
        })
        binding.viewPager.adapter = adapter
    }
}