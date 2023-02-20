package com.example.filmflows.ui.pantallas.movies

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
import com.example.filmflows.domain.modelo.Movie
import com.example.filmflows.ui.pantallas.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var adapter: MoviesAdapter

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.popularMovies)
        viewModel.handleEvent(MoviesEvent.GetPopularMovies)

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
                        viewModel.handleEvent(MoviesEvent.ErrorCaught)
                    }
                    binding.loading.visibility =
                        if (value.isLoading) View.VISIBLE else View.GONE
                    adapter.submitList(value.movies)
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

        adapter = MoviesAdapter(object : MoviesAdapter.MovieActions {
            override fun onMovieWatch(movie: Movie) {
                val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(movie.id)
                findNavController().navigate(action)
            }
        })
        binding.viewPager.adapter = adapter
    }

}