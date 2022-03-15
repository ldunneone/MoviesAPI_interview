package com.luke.movies.ui.movies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.luke.movies.R
import com.luke.movies.helpers.extensions.visible
import com.luke.movies.helpers.network.NetworkUtil
import com.luke.movies.ui.movieDetails.MovieDetailFragment.Companion.REQUEST_MOVIE_ID
import com.luke.movies.ui.movieDetails.MovieDetailsActivity
import com.luke.movies.viewModels.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 */
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var thisView: View
    private var movieAdapter = MoviesPagedAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisView = inflater.inflate(R.layout.fragment_movies, container, false)

        observeData()

        return thisView
    }

    /**
     *
     */
    private fun observeData() {
        setUpRecyclerView()

        viewModel.moviePagedList.observe(viewLifecycleOwner, {
            movieAdapter.submitList(it)
        })

        viewModel.output.loading.observe(viewLifecycleOwner, {
            thisView.progress_circular.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.output.error.observe(viewLifecycleOwner, {
            thisView.error.text = it
            it?.let { thisView.error.visible() }
        })

        if (!NetworkUtil.isInternetAvailable)
            Toast.makeText(requireContext(), getString(R.string.error_internet), Toast.LENGTH_SHORT)
                .show()
    }

    private fun setUpRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        thisView.moviesRecyclerView.layoutManager = gridLayoutManager
        thisView.moviesRecyclerView.setHasFixedSize(true)
        thisView.moviesRecyclerView.adapter = movieAdapter
        movieAdapter.onMovieClicked = {
            it?.let { movie ->
                openMovieDetails(movie.id)
            }
        }
    }

    private fun openMovieDetails(movieId: Int) {
        activity?.let {
            it.startActivity(
                Intent(it, MovieDetailsActivity::class.java)
                    .putExtra(REQUEST_MOVIE_ID, movieId)
            )
        }
    }
}