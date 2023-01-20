package com.example.filmflows.ui.pantallas.content

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmflows.databinding.FragmentContentBinding
import com.example.filmflows.domain.modelo.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentFragment : Fragment() {

    private lateinit var binding: FragmentContentBinding
    private lateinit var adapter: MoviesAdapter

    private val viewModel: ContentFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleEvent(ContentFragmentEvent.GetPopularMovies)

        init()
        binding.list.adapter = adapter

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
                    adapter.submitList(value.movies)
                }
            }
        }
    }

    private fun init() {
        val layoutManager = LinearLayoutManager(this.context)
        binding.list.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            binding.list.context,
            layoutManager.orientation
        )
        binding.list.addItemDecoration(dividerItemDecoration)

        adapter = MoviesAdapter(object : MoviesAdapter.MovieActions {
            override fun onMovieWatch(movie: Movie) {

            }
        })



    }

}