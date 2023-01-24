package com.example.filmflows.ui.pantallas.seriesDetail

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
import com.bumptech.glide.Glide
import com.example.filmflows.R
import com.example.filmflows.common.Constantes
import com.example.filmflows.databinding.FragmentSeriesDetailBinding
import com.example.filmflows.ui.pantallas.MainActivity
import com.example.filmflows.ui.pantallas.movies.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeriesDetailFragment : Fragment() {

    private lateinit var binding: FragmentSeriesDetailBinding

    private val viewModel: SeriesDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeriesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.series)

        val id = arguments?.getInt(Constantes.idSeries) ?: 0
        viewModel.handleEvent(SeriesDetailEvent.GetSeriesDetail(id))

        with(binding) {

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
                        loading.visibility =
                            if (value.isLoading) View.VISIBLE else View.GONE
                        if (value.series != null) {
                            value.series.let {
                                Glide.with(requireContext())
                                    .load(Constantes.imageUrl + it.poster_path)
                                    .into(poster)
                                textName.text = it.name
                                textVote.text = it.vote_average.toString()
                                textDate.text = it.first_air_date
                                textOverview.text = it.overview
                            }
                        }
                    }
                }
            }
        }
    }

}