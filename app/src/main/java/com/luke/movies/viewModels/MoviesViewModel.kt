package com.luke.movies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.luke.movies.base.output.SimpleOutput
import com.luke.movies.data.models.movieDetails.MovieDetails
import com.luke.movies.data.models.movies.Movie
import com.luke.movies.data.repositories.movies.MoviesRepository
import io.reactivex.disposables.CompositeDisposable

/**
 *
 */
class MoviesViewModel(private val movieRepository: MoviesRepository) : ViewModel() {

    /**
     *
     */
    private val compositeDisposable = CompositeDisposable()
    var output = SimpleOutput<MovieDetails, String>()

    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    init {
        output = movieRepository.output
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}